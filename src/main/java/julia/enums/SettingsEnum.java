package julia.enums;

import by.tehnologia.regexp.Regexp;

/**
 * Enum для работы с настройками системы.
 * @author Aleksandr Golovnyov
 */
public enum SettingsEnum {

    COMMON_PASSWORD_DEFAULT("common.password.default", ".{4,}", "Пароль по умолчанию"),
    COMMON_PAGINATION_LIMIT("common.pagination.limit", "[1-9]{1}[0-9]{1,3}", "Количество отображаемых записей пагинации"),
    COMMON_ORGANIZATION_NAME("common.organization.name", null, "Название организации"),
    COMMON_FILE_MAXSIZE("common.file.maxSize", "[0-9]+", "Максимальный размер на загружаемый файл, Мбайт"),

    // FIXME: нужно изменить имя параметра в соответствии с назначением.
    // Сейчас данный параметр означает частоту обновления UI для показаний
    MAP_UI_UPDATE_RATE("temperatureSensor.pollingRate", Regexp.FOUR_DIGIT_NUMBER, ""),
    TEMPERATURE_SENSOR_THRESHOLD_MEASUREMENTS_AMOUNT(
            "temperatureSensor.threshold.measurementsAmount", Regexp.TWO_DIGIT_NUMBER, ""),
    GSM_DEVICE_COM_PORT("gsmDevice.comPort", ".+", ""),
    MICROCLIMATE_MAP_FILE_UPLOADING_RATE("microclimateMap.fileUploadingRate", Regexp.TWO_DIGIT_NUMBER, ""),

    MAIL_USERNAME("mail.username", Regexp.EMAIL, ""),
    MAIL_PASSWORD("mail.password", ".+", ""),
    MAIL_HOST("mail.smtp.host", Regexp.HOSTNAME_OR_IP_ADDRESS, ""),
    MAIL_PORT("mail.smtp.port", "[1-9]{1}[0-9]{1,4}", ""),
    MAIL_TLS("mail.smtp.starttls.enable", Regexp.BOOLEAN_VALUE, ""),
    MAIL_TIMEOUT("mail.smtp.timeout", Regexp.TWO_DIGIT_NUMBER, ""),

    NOTIFY_DELAY("notify.delay", Regexp.THREE_DIGIT_NUMBER, ""),
    STORAGE_PERIOD("storage_period", Regexp.ONLY_DIGITS, ""),
    JOURNALS_STORAGE_PERIOD("journals.storagePeriod", Regexp.ONLY_DIGITS, ""),
    FILE_DATA_LOAD_USERS_EDUCATIONS_PATH("file.data.load.users.educations.path", null, ""),
    FILE_DATA_LOAD_USERS_POSTS_PATH("file.data.load.users.posts.path", null, ""),
    FILE_DATA_LOAD_DEPARTMENTS_PATH("file.data.load.departments.path", null, ""),
    FILE_DATA_LOAD_DEPARTMENTS_POSTS_PATH("file.data.load.departments.posts.path", null, ""),
    FILE_DATA_LOAD_DEPARTMENTS_EQUIPMENTS_PATH("file.data.load.equipments.path", null, "");

    private String key;
    private String pattern;
    private String name;

    SettingsEnum(String key, String pattern, String name) {
        this.key = key;
        this.pattern = pattern;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getPattern() {
        return pattern;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return key;
    }

    public static SettingsEnum valueOfKey(String key) {

        SettingsEnum[] values = SettingsEnum.values();

        for (SettingsEnum value : values) {
            if (value.key.equals(key)) {
                return value;
            }
        }

        return null;
    }
}
