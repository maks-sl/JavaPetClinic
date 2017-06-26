package ru.lesson.lessons;

public class Cat extends Animal{
    public Cat(int id, int clientId, String name) {
        super(id, clientId, name, PetType.getIdByPetType(PetType.CAT));
    }

}