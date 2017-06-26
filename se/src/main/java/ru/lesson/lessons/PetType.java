package ru.lesson.lessons;

/**
 * Список доступных типов животных
 */
public enum PetType {
    /** Кот */
    CAT,
    /** Собака */
    DOG,
    /** Животное не из списка */
    SOME_PET;

    /**
     * Строковые константы для каждого типа
     */
    private static final int CONST_CAT = 1;
    private static final int CONST_DOG = 2;
    private static final int CONST_SOME_PET = 0;

    /**
     * Получить тип животного по id
     * @param typeId Строка выбора типа
     * @return Тип животного
     */
    public static PetType getTypeById(int typeId) {
        switch (typeId) {
            case CONST_CAT:
                return CAT;
            case CONST_DOG:
                return DOG;
            default:
                return SOME_PET;
        }
    }

    public static int getIdByPetType (PetType petType) {
        switch (petType) {
            case CAT:
                return CONST_CAT;
            case DOG:
                return CONST_DOG;
            default:
                return CONST_SOME_PET;
        }
    }

    public static PetType getTypeByName(String name){
        switch (name) {
            case "CAT":
                return CAT;
            case "DOG":
                return DOG;
            default:
                return SOME_PET;
        }
    }
}