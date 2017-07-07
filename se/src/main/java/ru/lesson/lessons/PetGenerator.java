//package ru.lesson.lessons;
//
///**
// * Created by User on 25.06.2017.
// * Класс для генерации экземпляра животного
// */
//public class PetGenerator {
//
//    /**
//     * В зависимости от переданного параметра PetType создает экземпляр животного требуемого класса
//     * @param id id питомца
//     * @param clientId id клиента
//     * @param name имя клиента
//     * @param type параметр PetType отвечающий за тип создаваемого животного
//     * @return экземпляр животного
//     */
//    public static Pet createPet(int id, int clientId, String name, PetType type) {
//        switch (type) {
//            case CAT:
//                return new Cat(id, clientId, name);
//            case DOG:
//                return new Dog(id, clientId, name);
//            default:
//                return new SomePet(id, clientId, name);
//        }
//    }
//
//}
