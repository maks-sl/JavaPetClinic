package ru.lesson.store;

import java.util.Collection;

/**
 * Описание базовых методов хранилища характерных для любой сущности
 */
public interface BaseStorage<T> {

    /**
     * Возвращает всех объектов
     * @return Коллекция объектов
     */
    Collection<T> values();

    /**
     * Добавляет объект в хранилище
     * @param object объект
     * @return id добавленного в хранилище нового питомца
     */
    int add(final T object);

    /**
     * Изменение объекта.
     * Заменяет существующий экземпляр объекта (с таким же id как у передаваемого)
     * @param object новый экземпляр объекта
     */
    void edit(final T object);

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
    T get(final int id);

    /**
     * Функция закрытия ресурсов
     */
    void close();
}
