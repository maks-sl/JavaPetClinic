package ru.lesson.models;

/**
 * Модель формы поиска клиентов
 */
public class SearchForm {

    /** имя клиента для поиска */
    private String findClientName;
    /** имя животного для поиска клиента*/
    private String findPetName;
    /** условие поиска И */
    private String isAnd;

    public String getFindClientName() {
        return findClientName;
    }

    public void setFindClientName(String findClientName) {
        this.findClientName = findClientName;
    }

    public String getFindPetName() {
        return findPetName;
    }

    public void setFindPetName(String findPetName) {
        this.findPetName = findPetName;
    }

    public String getIsAnd() {
        return isAnd;
    }

    public void setIsAnd(String isAnd) {
        this.isAnd = isAnd;
    }
}
