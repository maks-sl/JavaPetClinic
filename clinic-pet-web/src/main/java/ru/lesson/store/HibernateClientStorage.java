package ru.lesson.store;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ru.lesson.models.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Класс хранения клиентов в БД использующий Hibernate
 * Назначение @Override методов описано в интерфейсе ClientStorage
 */
@Repository
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

    /**
     * В конструкторе инициализируем sessionFactory
     */
    HibernateClientStorage() {
        sessionFactory =  createSessionFactory();
    }

    /**
     * Шаблон проектирования Commander
     * Интерфейс Command, представляющий нашу команду для выполнения
     * содержит только один метод process, который запускается для исполнения
     * Метод process мы будем реализовывать в анонимном классе передвавемом в функцию transaction
     * @param <T> тип-параметр, указывает на возвращаемый
     */
    public interface Command<T> {
        T process(Session session);
    }

    /**
     * Шаблон проектирования Commander
     * Функция-коммандер для обращения к БД
     * открывает сессию и транзакцию
     * выполняет в блоке try переданную команду и возвращает результат
     * после чего finally закрывает сессию и транзакцию
     *
     * @param command Команда
     * @param <T> Тип-параметр
     * @return результат работа команды
     */
    private <T> T transaction(final Command<T> command){
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return command.process(session);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Collection<Client> values() {
        return transaction((Session session) -> session.createQuery("from Client").list() );
    }

    @Override
    public int add(String name, String surname, String email, int gender) {
        Client client = new Client(0, name, surname, email, gender);
        return transaction((Session session) -> {
            session.save(client);
            return client.getId();
        } );
    }

    @Override
    public void edit(Client client) {
        transaction((Session session) -> {session.update(client); return null;});
    }

    @Override
    public void delete(int id) {
        transaction((Session session) -> {session.delete(this.get(id)); return null;});
    }

    @Override
    public Client get(int id) {
        return transaction((Session session) -> (Client) session.get(Client.class, id));
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
        return transaction((Session session) -> {
            final Query query = session.createQuery("from Client as client where lower(client.name || ' ' || client.surname) like :clientName");
            query.setString("clientName", "%"+clientName.toLowerCase()+"%");
            return (Collection<Client>) query.list();} );
    }

    @Override
    public Collection<Client> searchByPetName(String petName) {
        return transaction((Session session) -> {
            final Query query = session.createQuery("select c from Client as c inner join c.pets as p where lower(p.name) like :petName");
            query.setString("petName", "%"+petName.toLowerCase()+"%");
            return (Collection<Client>) query.list();} );
    }

    @Override
    public void close() {
        this.sessionFactory.close();
    }
}
