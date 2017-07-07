//package ru.lesson.lessons;
//
//public class Animal implements Pet{
//
//    /** Имя животного */
//    private final String name;
//
//    /** id животного */
//    private final int id;
//
//    /** id владельца животного */
//    private final int clientId;
//
//    /** id типа животного */
//    private final int typeId;
//
//    /** Конструктор
//     * @param id id животного
//     * @param clientId id владельца
//     * @param name имя животного
//     * @param typeId id типа животного
//     */
//    public Animal(int id, int clientId, String name, int typeId) {
//        if(name.isEmpty()) throw new IllegalArgumentException("Pet name can not be empty");
//        this.id = id;
//        this.clientId = clientId;
//        this.name = name;
//        this.typeId = typeId;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public int getId() {
//        return id;
//    }
//
//    @Override
//    public int getClientId() {
//        return this.clientId;
//    }
//
//    @Override
//    public int getTypeId() {
//        return this.typeId;
//    }
//
//}