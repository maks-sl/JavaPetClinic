package ru.lesson.store;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.lesson.lessons.PetGenerator;
import ru.lesson.lessons.PetType;
import ru.lesson.models.Pet;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by User on 29.06.2017.
 */
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

    HibernatePetStorage() {
        sessionFactory =  createSessionFactory();
    }

    public interface Command<T> {
        T process(Session session);
    }

    private <T> T transaction(final HibernateClientStorage.Command<T> command){
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
    public Collection<ru.lesson.lessons.Pet> values() {
        return transaction((Session session) -> session.createQuery("from Pet").list() );
    }

    @Override
    public Collection<ru.lesson.lessons.Pet> getByClientId(int clientId) {
        return transaction((Session session) -> {
            Collection<ru.lesson.lessons.Pet> toReturn = new HashSet<>();
            final Query query = session.createQuery("from Pet p where p.owner.id = :clientId");
            query.setInteger("clientId", clientId);
            Collection<Pet> toPets = query.list();
            for(Pet toPet: toPets){
                ru.lesson.lessons.Pet pet = PetGenerator.createPet(
                        toPet.getId(),
                        toPet.getOwner().getId(),
                        toPet.getName(),
                        PetType.getTypeById(toPet.getType_id()));
                toReturn.add(pet);
            }
            return toReturn;
        } );
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
