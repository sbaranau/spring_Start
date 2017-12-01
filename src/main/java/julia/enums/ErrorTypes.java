package julia.enums;

/**
 * Типы ексепшенов
 */
public enum ErrorTypes {

    VALIDATION_ERROR("Ошибка валидации"),
    API_ERROR("Ошибка"),
    ACCESS_DENIED_ERROR("Ошибка доступа");

    ErrorTypes(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
