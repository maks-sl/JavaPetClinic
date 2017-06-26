package ru.lesson.lessons;

/**
 * Created by User on 25.06.2017.
 */
public class PetGenerator {

    public static Pet createPet(int id, int clientId, String name, PetType type) {
        switch (type) {
            case CAT:
                return new Cat(id, clientId, name);
            case DOG:
                return new Dog(id, clientId, name);
            default:
                return new SomePet(id, clientId, name);
        }
    }

}
