package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.models.Pet;

import java.util.Collection;

/**
 * Класс хранения животных в БД использующий Hibernate
 * Назначение @Override методов описано в интерфейсе PetStorage
 */
@Repository
@Transactional
public class HTPetStorage implements IHTPetStorage{

    private final HibernateTemplate ht;

    /**
     * В конструкторе инициализируем HibernateTemplate
     */
    @Autowired
    public HTPetStorage(HibernateTemplate ht) {
        this.ht = ht;
    }

    @Override
    public Collection<Pet> values() {
        return (Collection<Pet>) this.ht.find("from Pet");
    }

    @Override
    public Collection<Pet> getByClientId(int clientId) {
        return (Collection<Pet>) this.ht.findByNamedParam("from Pet p join fetch p.petType as pt where p.owner.id = :clientId", "clientId", clientId);
    }

    @Override
    public int add(Pet pet) {
        return (int) this.ht.save(pet);
    }

    @Override
    public void edit(Pet pet) {
        this.ht.update(pet);
    }

    @Override
    public void delete(int id) {
        this.ht.delete(this.ht.get(Pet.class, id));
    }

    @Override
    public Pet get(int id) {
        return  this.ht.get(Pet.class, id);
    }

    @Override
    public void close() {}
}
