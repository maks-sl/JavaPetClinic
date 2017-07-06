package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

        return makePetsImplementation((Collection<Pet>) this.ht.find("from Pet"));
    }

    @Override
    public Collection<ru.lesson.lessons.Pet> getByClientId(int clientId) {

        return makePetsImplementation((Collection<Pet>) this.ht.findByNamedParam("from Pet p where p.owner.id = :clientId", "clientId", clientId));
    }

    @Override
    public int add(int clientId, String name, PetType type) {
        Pet pet = new Pet(
                0,
                name,
                PetType.getIdByPetType(type),
                ClientCache.getInstance().get(clientId));
        return (int) this.ht.save(pet);
    }

    @Override
    public void edit(ru.lesson.lessons.Pet toPet) {
        Pet pet = new Pet(
                toPet.getId(),
                toPet.getName(),
                toPet.getTypeId(),
                ClientCache.getInstance().get(toPet.getClientId()));
        this.ht.update(pet);
    }

    @Override
    public void delete(int id) {
        this.ht.delete(this.ht.get(Pet.class, id));
    }

    @Override
    public ru.lesson.lessons.Pet get(int id) {
        Pet toPet = (Pet) this.ht.get(Pet.class, id);
        ru.lesson.lessons.Pet pet = PetGenerator.createPet(
                toPet.getId(),
                toPet.getOwner().getId(),
                toPet.getName(),
                PetType.getTypeById(toPet.getType_id()));
        return pet;
    }

    @Override
    public void close() {}
}
