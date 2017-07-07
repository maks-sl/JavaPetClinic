package ru.lesson.models;

/**
 * Модель для форм добавления или редактирования животного
 */
public class PetForm {

    /** id животного */
    private String petId;
    /** id владельца животного */
    private String clientId;
    /** id типа животного */
    private String petTypeId;
    /** имя животного */
    private String petName;

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(String petTypeId) {
        this.petTypeId = petTypeId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
