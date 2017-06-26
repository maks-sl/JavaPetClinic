package ru.lesson.lessons;

public class Dog implements Pet{
    private Pet pet;

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
//        return this.getClass().getSimpleName();
    }

    @Override
    public int getTypeId() {
        return this.pet.getTypeId();
    }

}