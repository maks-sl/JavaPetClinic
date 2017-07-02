package ru.lesson.store;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.lesson.models.Client;

import java.util.Collection;

/**
 * Created by User on 29.06.2017.
 */
public class HibernateClientStorage implements ClientStorage{

    private SessionFactory sessionFactory;
    private org.hibernate.service.ServiceRegistry serviceRegistry;

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    HibernateClientStorage() {
        sessionFactory =  createSessionFactory();
    }

    @Override
    public Collection<Client> values() {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return session.createQuery("from Client").list();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public int add(String name, String surname, String email, int gender) {
        Client client = new Client(0, name, surname, email, gender);
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(client);
            return client.getId();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void edit(Client client) {

    }

    @Override
    public void delete(int id) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(new Client(id, "sample", "sample", "sample", 0));
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Client get(int id) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return (Client) session.get(Client.class, id);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Collection<Client> searchOr(String clientName, String petName) {
        return null;
    }

    @Override
    public Collection<Client> searchAnd(String clientName, String petName) {
        return null;
    }

    @Override
    public Collection<Client> searchByName(String clientName) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            final Query query = session.createQuery("from Client as client where client.name=:clientName");
            query.setString("clientName", clientName);
            return (Collection<Client>) query.list();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Collection<Client> searchByPetName(String petName) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            final Query query = session.createQuery("from Client as client inner join Pet as pet where pet.name=:petName");
            query.setString("petName", petName);
            return (Collection<Client>) query.list();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void close() {
        this.sessionFactory.close();
    }
}
