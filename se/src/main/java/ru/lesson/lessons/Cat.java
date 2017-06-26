package ru.lesson.lessons;

/**
 * Реализация кот
 */
public class Cat extends Animal{
    /**
     * Конструктор
     * @param id id животного
     * @param clientId id владельца
     * @param name имя животного
     */
    public Cat(int id, int clientId, String name) {
        super(id, clientId, name, PetType.getIdByPetType(PetType.CAT));
    }

}