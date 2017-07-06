package ru.lesson.store;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetType;

import java.util.Collection;

/**
 * Класс-Singleton для реализации хранилища животных
 */
public class PetCache implements PetStorage {

    /** выбор реализации хранилища */

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);
    private final PetStorage petStorage = storages.petStorage;
//    private final PetStorage petStorage = new HibernatePetStorage();
//    private final PetStorage petStorage = new JdbcPetStorage();
//    private final PetStorage petStorage = new MemoryPetStorage();

    /** инициализация единственного экземпляра */
    private static final PetCache INSTANCE = new PetCache();

    /**
     * закрытый конструктор, запрет на создание экземпляров извне класса
     */
    private PetCache(){}

    /**
     * Вернуть экземпляр singleton
     * @return экземпляр singleton
     */
    public static PetCache getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Pet> values() {
        return this.petStorage.values();
    }

    @Override
    public int add(int clientId, String name, PetType petType) {
        return this.petStorage.add(clientId, name, petType);
    }

    @Override
    public void edit(Pet pet) {
        this.petStorage.edit(pet);
    }

    @Override
    public void delete(int id) {
        this.petStorage.delete(id);
    }

    @Override
    public Pet get(int id) {
        return this.petStorage.get(id);
    }

    @Override
    public Collection<Pet> getByClientId(int clientId) {
        return this.petStorage.getByClientId(clientId);
    }

    @Override
    public void close() {
        this.petStorage.close();
    }
}
