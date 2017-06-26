package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.models.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryClientStorage implements ClientStorage {

    private final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();
    final AtomicInteger ids = new AtomicInteger();

    //relation with Pets
    private final PetCache PET_CACHE = PetCache.getInstance();

    private int getNextId() {
        return this.ids.incrementAndGet();
    }

    @Override
    public Collection<Client> values() {
        return this.clients.values();
    }

    @Override
    public int add(String name, String surname, String email, int gender) {
        int nid = this.getNextId();
        this.clients.put(nid, new Client(nid, name, surname, email, gender));
        return nid;
    }

    @Override
    public void edit(Client client) {
        this.clients.replace(client.getId(), client);
    }

    @Override
    public void delete(int id) {
        this.clients.remove(id);
    }

    @Override
    public Client get(int id) {
        return this.clients.get(id);
    }

    @Override
    public Collection<Client> searchOr(String clientName, String petName) {
        Collection<Client> toReturn = new ArrayList<>();
        toReturn.addAll(searchByName(clientName));
        toReturn.removeAll(searchByPetName(petName));
        toReturn.addAll(searchByPetName(petName));
        return toReturn;
    }

    @Override
    public Collection<Client> searchAnd(String clientName, String petName) {
        Collection<Client> toReturn = new ArrayList<>();
        Collection<Client> cPetName = searchByPetName(petName);
        for (Client c :searchByName(clientName)){
            if(cPetName.contains(c)) toReturn.add(c);
        }
        return toReturn;
    }

    @Override
    public Collection<Client> searchByName(String clientName) {
        Collection<Client> toReturn = new ArrayList<>();
        for (Client client: this.clients.values()){
            if(clientName != ""){
                if(client.getFullName().toLowerCase().contains(clientName.toLowerCase())) toReturn.add(client);
            }
        }
        return toReturn;
    }

    @Override
    public Collection<Client> searchByPetName(String petName) {

        Collection<Client> toReturn = new HashSet<>();
            if(petName != ""){
                for (Pet pet: this.PET_CACHE.values()){
                    if(pet.getName().toLowerCase().contains(petName.toLowerCase())) toReturn.add(this.clients.get(pet.getClientId()));
                }
            }
        return toReturn;
    }

    @Override
    public void close() {

    }
}
