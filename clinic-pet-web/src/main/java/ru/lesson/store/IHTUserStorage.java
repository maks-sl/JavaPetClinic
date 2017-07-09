package ru.lesson.store;

import ru.lesson.models.Appuser;

import java.util.Collection;

/**
 * Описание доступных методов хранилища User
 */
public interface IHTUserStorage {

    /**
     * Возвращает найденного пользователя по логину и паролю
     * @param login логин
     * @param password пароль
     * @return найденный пользователь
     */
    Appuser findOnAuth(final String login, final String password);

    /**
     * Возвращает всех объектов
     * @return Коллекция объектов
     */
    Collection<Appuser> values();

    /**
     * Добавляет объект в хранилище
     * @param object объект
     * @return id добавленного в хранилище нового объекта
     */
    int add(final Appuser object);

    /**
     * Изменение объекта.
     * Заменяет существующий экземпляр объекта (с таким же id как у передаваемого)
     * @param object новый экземпляр объекта
     */
    void edit(final Appuser object);

    /**
     * Удаляет экземпляр объекта из хранилища по id
     * @param id id объекта
     */
    void delete(final int id);

    /**
     * Возвращает экземпляр объекта по id
     * @param id id объекта
     * @return экземпляр объекта
     */
    Appuser get(final int id);

    /**
     * Функция закрытия ресурсов
     */
    void close();
}
