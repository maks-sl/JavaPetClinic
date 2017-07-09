package ru.lesson.models;

/**
 * Роль пользователя импользуемая при определении доступа в spring-security
 */
public class Role {

    /** id */
    private int id;
    /** имя */
    private String name;

    public Role() {
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
