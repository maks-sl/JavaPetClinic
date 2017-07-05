package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User on 05.07.2017.
 */
@Service
public class Storages {
    public final HibernateClientStorage clientStorage;
    public final HibernatePetStorage petStorage;

    @Autowired
    public Storages(final HibernateClientStorage clientStorage, final HibernatePetStorage petStorage) {
        this.clientStorage = clientStorage;
        this.petStorage = petStorage;
    }
}
