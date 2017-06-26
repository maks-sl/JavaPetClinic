package ru.lesson.lessons;

/**
 * Класс для животного другого типа(не из доступного списка)
 */
public class SomePet implements Pet {
    /**
     * Строка типа животного
     */
    private Pet pet;

    /**
     * Конструктор
     * @param name Имя животного
     */
    public SomePet(int id, int clientId, String name) {
        this.pet = new Animal(id, clientId, name, PetType.getIdByPetType(PetType.SOME_PET));
    }

    @Override
    public String getName(){
        return this.pet.getName();
    }

    @Override
    public int getId(){
        return this.pet.getId();
    }

    @Override
    public int getClientId() {
        return this.pet.getClientId();
    }

    @Override
    public PetType getType() {
//        return this.getClass().getSimpleName();
        return this.pet.getType();
    }

    @Override
    public int getTypeId() {
        return this.pet.getTypeId();
    }

    @Override
    public String toString() {
        return "Some Pet{" +
                "name='" + this.pet.getName() + '\'' +
                '}';
    }
}