package ru.lesson.store;

import ru.lesson.models.Client;

import java.util.Collection;

public interface ClientStorage {

    public Collection<Client> values();

//    public int getNextId();

//    public int add(final Client client);

    int add(String name, String surname, String email, int sex);

    public void edit(final Client client);

    public void delete(final int id);

    public Client get(final int id);

    public Collection<Client> searchOr(final String clientName, final String petName);

    public Collection<Client> searchAnd(final String clientName, final String petName);

    public Collection<Client> searchByName(final String clientName);

    public Collection<Client> searchByPetName(final String petName);

    public void close();
}
