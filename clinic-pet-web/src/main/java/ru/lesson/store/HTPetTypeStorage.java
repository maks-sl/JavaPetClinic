package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.models.PetType;

import java.util.Collection;

/**
 * Класс хранения типов животных в БД использующий Hibernate
 * Назначение @Override методов описано в интерфейсе BaseStorage
 */
@Repository
@Transactional
public class HTPetTypeStorage implements BaseStorage<PetType>{

    private final HibernateTemplate ht;

    /**
     * В конструкторе инициализируем HibernateTemplate
     */
    @Autowired
    public HTPetTypeStorage(HibernateTemplate ht) {
        this.ht = ht;
    }

    @Override
    public Collection<PetType> values() {
        return (Collection<PetType>) this.ht.find("from PetType");
    }

    @Override
    public int add(PetType petType) {
        return (int) this.ht.save(petType);
    }

    @Override
    public void edit(PetType petType) {
        this.ht.update(petType);
    }

    @Override
    public void delete(int id) {
        this.ht.delete(this.ht.get(PetType.class, id));
    }

    @Override
    public PetType get(int id) {
        return this.ht.get(PetType.class, id);
    }

    @Override
    public void close() {}
}
