package ru.lesson.store;

import ru.lesson.models.Client;

import java.util.Collection;

/**
 * Описание базовых методов хранилища для клиентов
 */
public interface ClientStorage {

    /**
     * Возвращает всех клиентов
     * @return Коллекция клиентов
     */
    Collection<Client> values();

//    /**
//     * Добавляет клиента в хранилище
//     * @param name имя клиента
//     * @param surname фамилия клиента
//     * @param email электронная почта клиента
//     * @param gender пол клиента
//     * @return id добавленного в хранилище нового клиента
//     */
//    int add(final String name, final String surname, final String email, final int gender);

    /**
     * Добавляет клиента в хранилище
     * @param client
     * @return id добавленного в хранилище нового клиента
     */
    int add(final Client client);

    /**
     * Изменение клиента.
     * Заменяет существующий экземпляр клиента (с таким же id как у передаваемого) на передаваемый экземпляр
     * @param client новый экземпляр клиента
     */
    void edit(final Client client);

    /**
     * Удаляет экземпляр клиента из хранилища по id
     * @param id id клиента
     */
    void delete(final int id);

    /**
     * Возвращает экземпляр клиента по id
     * @param id id клиента
     * @return экземпляр клиента
     */
    Client get(final int id);

    /**
     * Поиск клиентов у которых:
     *          - полное имя включет подстроку clientName
     *          ИЛИ
     *          - имя какого-либо питомца включет подстроку petName
     * @param clientName подстрока для поиска в полном имени
     * @param petName подстрока для поиска в именах животных
     * @return Коллекция найденных клиентов
     */
    Collection<Client> searchOr(final String clientName, final String petName);

    /**
     * Поиск клиентов у которых:
     *          - полное имя включет подстроку clientName
     *          И
     *          - имя какого-либо питомца включет подстроку petName
     * @param clientName подстрока для поиска в полном имени
     * @param petName подстрока для поиска в именах животных
     * @return Коллекция найденных клиентов
     */
    Collection<Client> searchAnd(final String clientName, final String petName);

    /**
     * Поиск клиентов у которых полное имя включет подстроку clientName
     * @param clientName подстрока для поиска в полном имени
     * @return Коллекция найденных клиентов
     */
    Collection<Client> searchByName(final String clientName);

    /**
     * Поиск клиентов у которых имя какого-либо питомца включет подстроку petName
     * @param petName подстрока для поиска в именах животных
     * @return Коллекция найденных клиентов
     */
    Collection<Client> searchByPetName(final String petName);

    /**
     * Функция закрытия ресурсов
     */
    void close();
}
