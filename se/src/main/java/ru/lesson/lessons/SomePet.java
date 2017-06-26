package ru.lesson.lessons;

/**
 * Реализация неопределенного животного (неизвестного типа)
 */
public class SomePet implements Pet {

    /**  экземпляр животного инициируемого при создании объекта */
    private final Pet pet;

    /**
     * Конструктор
     * @param id id животного
     * @param clientId id владельца
     * @param name имя животного
     */
    public SomePet(final int id, final int clientId, final String name) {
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
        return this.pet.getType();
    }

    @Override
    public int getTypeId() {
        return this.pet.getTypeId();
    }

}