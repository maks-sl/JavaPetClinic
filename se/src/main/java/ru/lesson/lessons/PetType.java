package ru.lesson.lessons;

/**
 * Перечисление типов животных
 */
public enum PetType {
    /** Кот */
    CAT,
    /** Собака */
    DOG,
    /** Какое-то животное */
    SOME_PET;

    /**
     * Id типов для хранения в БД
     */
    private static final int CONST_CAT = 1;
    private static final int CONST_DOG = 2;
    private static final int CONST_SOME_PET = 0;

    /**
     * Возвращает тип животного по id
     * @param typeId id для храниния в БД
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

    /**
     * Возвращает id типа животного
     * @param petType тип животного
     * @return id типа животного
     */

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
        switch (name.toUpperCase()) {
            case "CAT":
                return CAT;
            case "DOG":
                return DOG;
            default:
                return SOME_PET;
        }
    }
}