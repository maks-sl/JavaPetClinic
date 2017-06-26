package ru.lesson.store;

import ru.lesson.lessons.*;
import ru.lesson.models.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryPetStorage implements PetStorage {

    private final ConcurrentHashMap<Integer, Pet> pets = new ConcurrentHashMap<>();
    final AtomicInteger ids = new AtomicInteger();

    @Override
    public Collection<Pet> values() {
        return this.pets.values();
    }

    private int getNextId() {
        return this.ids.incrementAndGet();
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
