package ru.lesson.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.models.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Класс хранения клиентов в БД использующий HibernateTemplate
 * Назначение @Override методов описано в интерфейсе ClientStorage
 */
@Repository
@Transactional
public class HTClientStorage implements IHTClientStorage{

    private final HibernateTemplate ht;

    /**
     * В конструкторе инициализируем HibernateTemplate
     */
    @Autowired
    public HTClientStorage(HibernateTemplate ht) {
        this.ht = ht;
    }

    @Override
    public Collection<Client> values() {
        return (Collection<Client>) this.ht.find("select distinct c from Client as c LEFT JOIN FETCH c.pets as p left JOIN FETCH p.petType as pt");
    }

//    @Override
//    public int add(String name, String surname, String email, int gender) {
//        Client client = new Client();
//        client.setName(name);
//        client.setSurname(surname);
//        client.setEmail(email);
//        client.setGender(gender);
//        return (int) this.ht.save(client);
//    }


    @Override
    public int add(Client client) {
        return (int) this.ht.save(client);
    }

    @Override
    public void edit(Client client) {
        this.ht.update(client);
    }

    @Override
    public void delete(int id) {
        this.ht.delete(this.get(id));
    }

    @Override
    public Client get(int id) {
        return this.ht.get(Client.class, id);
    }

    @Override
    public Collection<Client> searchOr(String clientName, String petName) {
        Collection<Client> toReturn = new HashSet<>();
        toReturn.addAll(searchByName(clientName));
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
        Collection<Client> qwe = (Collection<Client>) this.ht.findByNamedParam(
                    "select distinct c from Client as c left join fetch c.pets as p left join fetch p.petType as pt where lower(c.name || ' ' || c.surname) like :clientName",
                    "clientName",
                    "%"+clientName+"%");

            return qwe;
    }

    @Override
    public Collection<Client> searchByPetName(String petName) {
        return (Collection<Client>) this.ht.findByNamedParam(
                "select distinct c from Client as c left join fetch c.pets as p left join fetch p.petType as pt where lower(p.name) like :petName",
                "petName",
                "%"+petName+"%");
    }

    @Override
    public void close() {}
}
