package ru.lesson.store;


import ru.lesson.models.Client;

import java.util.Collection;

/**
 * Created by User on 17.06.2017.
 */
public class ClientCache implements ClientStorage {

    private static final ClientCache INSTANCE = new ClientCache();
    private final ClientStorage clientStorage = new JdbcClientStorage();
//    private final ClientStorage clientStorage = new MemoryClientStorage();

    public static ClientCache getInstance() {
        return INSTANCE;
    }

    public Collection<Client> values() {
        return this.clientStorage.values();
    }

    @Override
    public int add(String name, String surname, String email, int gender) {
        return this.clientStorage.add(name, surname, email, gender);
    }

    public void edit(final Client client) {
        this.clientStorage.edit(client);
    }

    public void delete(final int id) {
        this.clientStorage.delete(id);
    }

    public Client get(final int id) {
        return this.clientStorage.get(id);
    }

    public Collection<Client> searchOr(final String clientName, final String petName) {
        return this.clientStorage.searchOr(clientName, petName);
    }

    public Collection<Client> searchAnd(final String clientName, final String petName) {
        return this.clientStorage.searchAnd(clientName, petName);
    }

    public Collection<Client> searchByName(final String clientName) {
        return this.clientStorage.searchByName(clientName);
    }

    public Collection<Client> searchByPetName(final String petName) {
        return this.clientStorage.searchByPetName(petName);
    }

    @Override
    public void close() {
        this.clientStorage.close();
    }


}
