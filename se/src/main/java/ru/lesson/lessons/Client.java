//package ru.lesson.lessons;
//
//import java.util.Set;
//
///**
// * Класс клиента для реализации клиники из интерфейса командной строки
//
// * НЕ используется для Web-проекта
// */
//public class Client{
//
//    /** имя клиента */
//    private String id;
//
//    /** питомцы клиента */
//    private Set<Pet> pets;
//
//    /**
//     * Конструктор
//     * @param id id клиента
//     * @param pets Set-коллекция животных
//     */
//    public Client(String id, Set<Pet> pets) {
//        this.id = id;
//        this.pets = pets;
//    }
//
//    /**
//     * Вернуть id клиента
//     * @return id клиента
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * Вернуть животных клиента
//     * @return Set-коллекция животных
//     */
//    public Set<Pet> getPets() {
//        return pets;
//    }
//
//    public synchronized void setId(String id) {
//        this.id = id;
//    }
//
//    @java.lang.Override
//    public java.lang.String toString() {
//
//        return "Client{"  + "\r\n" +
//                "     id='" + id + "'\r\n" +
//                "     pets=" + pets.toString() + "\r\n" +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Client client = (Client) o;
//
//        return id != null ? id.equals(client.id) : client.id == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }
//
////    /**
////     * добавляет питомца клиенту
////     * @param type тип животного - cat или dog
////     * @param name имя для животного
////     * @throws Exception  в случае если у клиента уже есть животное с таким именем
////     */
////    public synchronized void addPet(String type, String name) throws Exception {
////        for(Pet p: this.pets){
////            if(p.getName().equals(name)) {
////                throw new Exception("Pet with this name is already exist");
////            }
////        }
////        switch (type){
////            case "cat":
////                this.pets.add(new Cat(name));
////                break;
////            case "dog":
////                this.pets.add(new Dog(name));
////                break;
////            default: throw new Exception("Unknown pet type");
////        }
////    }
//
//    /**
//     * пытается найти у клиента питомца с этим именем
//     * @param findName имя для поиска
//     * @return экземпляр питомца в случае удачи
//     * @throws Exception если питомца с таким именем нет
//     */
//    public synchronized Pet findPetByName(String findName) throws Exception{
//        Pet toReturn = null;
//        for(Pet p: this.pets){
//            if(p.getName().equals(findName)) toReturn = p;
//        }
//        if(toReturn == null) throw new Exception("A client " + this.getId() + " has no animal with this name");
//        return toReturn;
//    }
//
//    /**
//     * удаляет питомца у клиента
//     * @param name имя по которому будем удалять
//     * @throws Exception если питомца с таким именем нет
//     */
//    public synchronized void delPet(String name) throws Exception{
//        Pet petToRemove = this.findPetByName(name);
//        this.pets.remove(petToRemove);
//    }
//
////    /**
////     * удаляет питомца у клиента
////     * @param currentName имя по которому будем переименовывать
////     * @param newName новое имя
////     * @throws Exception если питомца с таким именем currentName нет
////     */
////    public synchronized void renamePet(String currentName, String newName) throws Exception{
////        Pet petToRename = this.findPetByName(currentName);
////        petToRename.setName(newName);
////    }
//
//}