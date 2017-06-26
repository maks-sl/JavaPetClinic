package ru.lesson.lessons;

/**
 * Описание базовых методов животного
 */
public interface Pet{

    /**
     * Вернуть имя животного
     * @return имя животного
     */
    String getName();

    /**
     * Вернуть id животного
     * @return id животного
     */
    int getId();

    /**
     * Вернуть id владельца животного
     * @return id владельца животного
     */
    int getClientId();

    /**
     * Вернуть id типа животного
     * @return id типа животного
     */
    int getTypeId();

    /**
     * Вернуть тип животного
     * @return тип животного
     */
    PetType getType();

}