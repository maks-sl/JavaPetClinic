package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetType;

import java.util.Collection;

/**
 * Created by User on 23.06.2017.
 */
public class PetCache implements PetStorage {

    private static final PetCache INSTANCE = new PetCache();

    public static PetCache getInstance() {
        return INSTANCE;
    }
    private final PetStorage petStorage = new JdbcPetStorage();
//    private final PetStorage petStorage = new MemoryPetStorage();

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
