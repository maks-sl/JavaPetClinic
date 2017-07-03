package ru.lesson.store;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.lesson.models.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(client);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(this.get(id));
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
        Collection<Client> toReturn = new HashSet<>();
        toReturn.addAll(searchByName(clientName));
        toReturn.addAll(searchByPetName(petName));
        return toReturn;
    }

    @Override
    public Collection<Client> searchAnd(String clientName, String petName) {
        Collection<Client> toReturn = new ArrayList<>();
        Collection<Client> cPetName = searchByPetName(petName);
        for (Client c :searchByName(clientName)){
            if(cPetName.contains(c)) toReturn.add(c);
        }
        return toReturn;
    }

    @Override
    public Collection<Client> searchByName(String clientName) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            //TODO: поиск по имени и фамилии
            final Query query = session.createQuery("from Client as client where lower(client.name) like :clientName");
            query.setString("clientName", "%"+clientName.toLowerCase()+"%");
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
            final Query query = session.createQuery("from Client as c inner join Pet as p on p.owner = c where lower(p.name) like :petName");
            query.setString("petName", "%"+petName.toLowerCase()+"%");
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
