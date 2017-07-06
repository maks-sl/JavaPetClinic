package ru.lesson.store;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lesson.models.Client;

import java.util.Collection;

/**
 * Класс-Singleton для реализации хранилища клиентов
 */
public class ClientCache implements ClientStorage {

    /** выбор реализации хранилища */
    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    Storages storages = context.getBean(Storages.class);
    private final ClientStorage clientStorage = storages.clientStorage;
//    private final ClientStorage clientStorage = new HibernateClientStorage();
//    private final ClientStorage clientStorage = new JdbcClientStorage();
//    private final ClientStorage clientStorage = new MemoryClientStorage();

    /** инициализация единственного экземпляра */
    private static final ClientCache INSTANCE = new ClientCache();

    /**
     * закрытый конструктор, запрет на создание экземпляров извне класса
     */
    private ClientCache(){}

    /**
     * Вернуть экземпляр singleton
     * @return экземпляр singleton
     */
    public static ClientCache getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Client> values() {
        return this.clientStorage.values();
    }

    @Override
    public int add(String name, String surname, String email, int gender) {
        return this.clientStorage.add(name, surname, email, gender);
    }

    @Override
    public void edit(final Client client) {
        this.clientStorage.edit(client);
    }

    @Override
    public void delete(final int id) {
        this.clientStorage.delete(id);
    }

    @Override
    public Client get(final int id) {
        return this.clientStorage.get(id);
    }

    @Override
    public Collection<Client> searchOr(final String clientName, final String petName) {
        return this.clientStorage.searchOr(clientName, petName);
    }

    @Override
    public Collection<Client> searchAnd(final String clientName, final String petName) {
        return this.clientStorage.searchAnd(clientName, petName);
    }

    @Override
    public Collection<Client> searchByName(final String clientName) {
        return this.clientStorage.searchByName(clientName);
    }

    @Override
    public Collection<Client> searchByPetName(final String petName) {
        return this.clientStorage.searchByPetName(petName);
    }

    @Override
    public void close() {
        this.clientStorage.close();
    }

}
