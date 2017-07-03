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

    @Override
    public Collection<ru.lesson.lessons.Pet> values() {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return session.createQuery("from Pet").list();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Collection<ru.lesson.lessons.Pet> getByClientId(int clientId) {
        Collection<ru.lesson.lessons.Pet> toReturn = new HashSet<>();
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
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
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public int add(int clientId, String name, PetType type) {
        Pet pet = new Pet(
                0,
                name,
                PetType.getIdByPetType(type),
                ClientCache.getInstance().get(clientId));
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(pet);
            return pet.getId();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void edit(ru.lesson.lessons.Pet toPet) {
        Pet pet = new Pet(
                toPet.getId(),
                toPet.getName(),
                toPet.getTypeId(),
                ClientCache.getInstance().get(toPet.getClientId()));
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(pet);
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
    public ru.lesson.lessons.Pet get(int id) {
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {

            Pet toPet = (Pet) session.get(Pet.class, id);
            ru.lesson.lessons.Pet pet = PetGenerator.createPet(
                    toPet.getId(),
                    toPet.getOwner().getId(),
                    toPet.getName(),
                    PetType.getTypeById(toPet.getType_id()));
            return pet;
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
