package ru.lesson.models;

/**
 * Пользователь используемый в Hibernate
 */
public class User {

    /** id клиента */
    private int id;
    /** имя клиента */
    private String name;
    /** фамилия клиента */
    private String surname;
    /** email клиента */
    private String email;
    /** пол клиента */
    private int gender;
    /** роль клиента */
    private Role role;
}
