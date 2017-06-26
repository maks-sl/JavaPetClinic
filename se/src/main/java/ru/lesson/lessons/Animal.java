package ru.lesson.lessons;

public class Animal implements Pet{

    private final String name;
    private final int id;
    private final int clientId;
    private final int typeId;

    public Animal(int id, int clientId, String name, int typeId) {
        if(name.isEmpty()) throw new IllegalArgumentException("Pet name can not be empty");
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.typeId = typeId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public int getTypeId() {
        return this.typeId;
    }

    public PetType getType() {
        return PetType.getTypeById(this.typeId);
    }
}