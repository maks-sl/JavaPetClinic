package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetGenerator;
import ru.lesson.lessons.PetType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryPetStorage implements PetStorage {

    /** Набор животных */
    private final ConcurrentHashMap<Integer, Pet> pets = new ConcurrentHashMap<>();

    /** Переменная хранящая id последнего добавленного животного */
    final AtomicInteger ids = new AtomicInteger();

    /**
     * Получение следующего порядкового id животного
     * Используется при добавлении нового животного
     * @return id для нового животного
     */
    private int getNextId() {
        return this.ids.incrementAndGet();
    }

    @Override
    public Collection<Pet> values() {
        return this.pets.values();
    }

    @Override
    public int add(int clientId, String name, PetType type) {
        Pet newPet = PetGenerator.createPet(this.getNextId(),clientId,name,type);
        this.pets.put(newPet.getId(), newPet);
        return newPet.getId();
    }

    @Override
    public Collection<Pet> getByClientId(int clientId) {
        Collection<Pet> toReturn = new ArrayList<>();
        for (Pet pet: this.pets.values()){
            if(pet.getClientId() == clientId){
                toReturn.add(pet);
            }
        }
        return toReturn;
    }

    @Override
    public void edit(Pet pet) {
        this.pets.replace(pet.getId(), pet);
    }

    @Override
    public void delete(int id) {
        this.pets.remove(id);
    }

    @Override
    public Pet get(int id) {
        return this.pets.get(id);
    }

    @Override
    public void close() {

    }
}
