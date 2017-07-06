package ru.lesson.store;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ru.lesson.lessons.PetGenerator;
import ru.lesson.lessons.PetType;
import ru.lesson.models.Pet;

import java.util.Collection;
import java.util.HashSet;

/**
 * Класс хранения животных в БД использующий Hibernate
 * Назначение @Override методов описано в интерфейсе PetStorage
 */
@Repository
public class HibernatePetStorage implements PetStorage{

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
    HibernatePetStorage() {
        sessionFactory =  createSessionFactory();
    }

    /**
     * Шаблон проектирования Commander
     * Интерфейс Command, представляющий нашу команду для выполнения
     * содержит только один метод process, который запускается для исполнения
     * Метод process мы будем реализовывать в анонимном классе передававемом в функцию transaction
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

    /**
     * Преобразует колеекцию записей из таблицы Pet
     * в коллекцию объектов-реализаций интерфейса Pet (Cat, Dog, etc..)
     * @param toPets коллекция записей из таблицы Pet на основании которой создаются экземпляры животных конкретных классов
     * @return коллекция животных представленных экземплярами конкретных классов (Cat, Dog, etc..)
     */
    private Collection<ru.lesson.lessons.Pet> makePetsImplementation(Collection<Pet> toPets){
        Collection<ru.lesson.lessons.Pet> toReturn = new HashSet<>();
        for(Pet toPet: toPets){
            ru.lesson.lessons.Pet pet = PetGenerator.createPet(
                    toPet.getId(),
                    toPet.getOwner().getId(),
                    toPet.getName(),
                    PetType.getTypeById(toPet.getType_id()));
            toReturn.add(pet);
        }
        return toReturn;
    }

    @Override
    public Collection<ru.lesson.lessons.Pet> values() {

        return makePetsImplementation(
                (Collection<Pet>) transaction((Session session) -> session.createQuery("from Pet").list())
        );
    }

    @Override
    public Collection<ru.lesson.lessons.Pet> getByClientId(int clientId) {
        return makePetsImplementation(
                (Collection<Pet>) transaction((Session session) -> {
                    Collection<ru.lesson.lessons.Pet> toReturn = new HashSet<>();
                    final Query query = session.createQuery("from Pet p where p.owner.id = :clientId");
                    query.setInteger("clientId", clientId);
                    return query.list();

                } )
        );
    }

    @Override
    public int add(int clientId, String name, PetType type) {
        Pet pet = new Pet(
                0,
                name,
                PetType.getIdByPetType(type),
                ClientCache.getInstance().get(clientId));
        return transaction((Session session) -> {
            session.save(pet);
            return pet.getId();
        } );
    }

    @Override
    public void edit(ru.lesson.lessons.Pet toPet) {
        Pet pet = new Pet(
                toPet.getId(),
                toPet.getName(),
                toPet.getTypeId(),
                ClientCache.getInstance().get(toPet.getClientId()));
        transaction((Session session) -> {session.update(pet); return null;});
    }

    @Override
    public void delete(int id) {
        transaction((Session session) -> {session.delete(session.get(Pet.class, id)); return null;});
    }

    @Override
    public ru.lesson.lessons.Pet get(int id) {
        return transaction((Session session) -> {
            Pet toPet = (Pet) session.get(Pet.class, id);
            ru.lesson.lessons.Pet pet = PetGenerator.createPet(
                    toPet.getId(),
                    toPet.getOwner().getId(),
                    toPet.getName(),
                    PetType.getTypeById(toPet.getType_id()));
            return pet;
        });
    }

    @Override
    public void close() {
        this.sessionFactory.close();
    }
}
