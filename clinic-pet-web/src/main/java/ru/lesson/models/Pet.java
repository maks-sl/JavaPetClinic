package ru.lesson.models;

/**
 * Животное используемое в Hibernate
 */
public class Pet{
    /** id животного */
    private int id;
    /** имя животного */
    private String name;
    /** тип животного */
    private PetType petType;
    /** владелец животного */
    private Client owner;

    /**
     * Пустой конструктор
     */
    public Pet() {
    }

    /**
     * Конструктор
     * @param id id животного
     * @param name имя животного
     * @param petType тип животного
     * @param owner владелец
     */
    public Pet(int id, String name, PetType petType, Client owner) {
        if(name.isEmpty() || owner.equals(null)) throw new IllegalArgumentException("Some of the arguments wrong");
        this.id = id;
        this.name = name;
        this.petType = petType;
        this.owner = owner;
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

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        return id == pet.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}



