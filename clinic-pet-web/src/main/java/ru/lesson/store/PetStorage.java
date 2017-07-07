package ru.lesson.store;

import ru.lesson.models.Pet;

import java.util.Collection;

/**
 * Описание базовых методов хранилища для животных
 */
public interface PetStorage {

    /**
     * Возвращает всех питомцев всех клиентов
     * @return Коллекция питомцев
     */
    Collection<Pet> values();

    /**
     * Возвращает питомцев по id владельца
     * @param clientId id владельца
     * @return Коллекция питомцев
     */
    Collection<Pet> getByClientId(final int clientId);

    /**
     * Добавляет питомца в хранилище
     * @param pet животное
     * @return id добавленного в хранилище нового питомца
     */
    int add(final Pet pet);

    /**
     * Изменение животного.
     * Заменяет существующий экземпляр животного (с таким же id как у передаваемого)
     * @param pet новый экземпляр животного
     */
    void edit(final Pet pet);

    /**
     * Удаляет экземпляр животного из хранилища по id
     * @param id id животного
     */
    void delete(final int id);

    /**
     * Возвращает экземпляр животного по id
     * @param id id животного
     * @return экземпляр животного
     */
    Pet get(final int id);

    /**
     * Функция закрытия ресурсов
     */
    void close();
}
