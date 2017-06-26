package ru.lesson.lessons;

/**
 * Реализация собака
 */
public class Dog implements Pet{
    private Pet pet;

    /**
     * Конструктор
     * @param id id животного
     * @param clientId id владельца
     * @param name имя животного
     */
    public Dog(int id, int clientId, String name) {
        this.pet = new Animal(id, clientId, name, PetType.getIdByPetType(PetType.DOG));
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
        return this.pet.getType();
    }

    @Override
    public int getTypeId() {
        return this.pet.getTypeId();
    }

}