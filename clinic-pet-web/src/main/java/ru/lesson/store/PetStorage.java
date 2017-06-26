package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetType;

import java.util.Collection;

public interface PetStorage {

    Collection<Pet> values();

    Collection<Pet> getByClientId(final int clientId);

//    public int getNextId();

    int add(int clientId, String name, PetType type);

//    int add(final Pet pet);

    void edit(final Pet pet);

    void delete(final int id);

    Pet get(final int id);

    void close();
}
