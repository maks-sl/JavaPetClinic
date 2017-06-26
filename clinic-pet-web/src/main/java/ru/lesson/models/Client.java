package ru.lesson.models;

import ru.lesson.lessons.Pet;
import ru.lesson.store.PetCache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    /** id клиента */
    private final int id;
    /** имя клиента */
    private final String name;
    /** фамилия клиента */
    private final String surname;
    /** email клиента */
    private final String email;
    /** пол клиента */
    private final int gender;

    /** питомцы клиента */
    private final PetCache PET_CACHE = PetCache.getInstance();


    public Client(int id, String name, String surname, String email, int gender) {
        if(name.isEmpty() || surname.isEmpty() || email.isEmpty()) throw new IllegalArgumentException("Some of the arguments wrong");
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getGender() {
        return gender;
    }

    public String getFullName(){
        return this.name + " " + this.surname;
    }

    public Collection<Pet> getPets() {
        return this.PET_CACHE.getByClientId(this.id);
    }

    /*public void addPet(Pet pet){
        this.PET_CACHE.add(pet);
    }*/

    @Override
    public String toString() {
        return "Client{"  + "\r\n" +
                "     id='" + id + "'\r\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return id == client.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}