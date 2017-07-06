package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User on 05.07.2017.
 */

@Service
public class Storages {
    public final IHTClientStorage clientStorage;
    public final IHTPetStorage petStorage;

    @Autowired
    public Storages(final IHTClientStorage clientStorage, final IHTPetStorage petStorage) {
        this.clientStorage = clientStorage;
        this.petStorage = petStorage;
    }
}
