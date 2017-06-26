package ru.lesson.store;

import ru.lesson.lessons.Pet;
import ru.lesson.lessons.PetType;

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
     * @param clientId id владельца животного
     * @param name имя животного
     * @param type параметр PetType определяющий тип животного
     * @return id добавленного в хранилище нового питомца
     */
    int add(final int clientId, final  String name, final  PetType type);

    /**
     * Изменение животного.
     * Заменяет существующий экземпляр животного (с таким же id как у передаваемого) на передаваемый экземпляр
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
