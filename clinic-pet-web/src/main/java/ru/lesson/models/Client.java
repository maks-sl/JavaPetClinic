package ru.lesson.models;

import ru.lesson.lessons.Pet;
import ru.lesson.store.PetCache;

import java.util.Collection;
import java.util.Set;

/**
 * Класс клиента
 */
public class Client {

    /** id клиента */
    private int id;
    /** имя клиента */
    private String name;
    /** фамилия клиента */
    private String surname;
    /** email клиента */
    private String email;
    /** пол клиента */
    private int gender;
    /** роль клиента */
    private Role role;
    /** животные клиента */
    private Set<ru.lesson.models.Pet> pets;

    /**
     * Пустой конструктор
     */
    public Client() {
    }

    /**
     * Конструктор
     * @param id id клиента
     * @param name имя клиента
     * @param surname фамилия клиента
     * @param email электронная почта клиента
     * @param gender пол клиента
     */
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<ru.lesson.models.Pet> getPets() {
        return pets;
    }

    public void setPets(Set<ru.lesson.models.Pet> pets) {
        this.pets = pets;
    }

    /**
     * Вернуть полное имя
     * @return полное имя в формате "имя фамилия"
     */
    public String getFullName(){
        return this.name + " " + this.surname;
    }

    /**
     * Вернуть набор животных для данного владельца
     * @return коллекция животных клиента
     */
    public Collection<Pet> getPetsOfClient() {
        return PetCache.getInstance().getByClientId(this.id);
    }

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