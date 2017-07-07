package ru.lesson.models;

/**
 * Тип животного используемго в Hibernate
 */
public class PetType {
    /** id */
    private int id;
    /** имя типа животного */
    private String name;

    public PetType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
