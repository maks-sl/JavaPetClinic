package ru.lesson.models;

/**
 * Модель пользователя приложения. Используется для авторизации в spring-security
 */
public class Appuser {

    /** id */
    private int id;
    /** login */
    private String login;
    /** password */
    private String password;
    /** role */
    private Role role;

    public Appuser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
