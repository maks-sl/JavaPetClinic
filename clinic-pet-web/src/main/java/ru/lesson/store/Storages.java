package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lesson.models.PetType;

/**
 * Created by User on 05.07.2017.
 */

@Service
public class Storages {
    public final IHTClientStorage clientStorage;
    public final IHTPetStorage petStorage;
    public final BaseStorage<PetType> petTypeStorage;
    public final IHTUserStorage userStorage;

    @Autowired
    public Storages(IHTClientStorage clientStorage, IHTPetStorage petStorage, BaseStorage<PetType> petTypeStorage, IHTUserStorage userStorage) {
        this.clientStorage = clientStorage;
        this.petStorage = petStorage;
        this.petTypeStorage = petTypeStorage;
        this.userStorage = userStorage;
    }
}
