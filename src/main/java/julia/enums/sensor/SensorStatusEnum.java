package julia.enums.sensor;


/**
 * @author Aleksandr Golovnyov
 */
public enum SensorStatusEnum {

    /** Датчик ативен */
    ACTIVE(1L, "sensor.active"),
    /** Датчик не активен */
    INACTIVE(2L, "sensor.inactive"),
    /** Датчик не отвечает */
    UNRESPONSIVE(3L, "sensor.unresponsive");

    private long id;
    private String code;

    SensorStatusEnum(long id, String code) {
        this.id = id;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public SensorStatusEnum getReverse() {

        if (this.equals(ACTIVE) || this.equals(UNRESPONSIVE)) {
            return INACTIVE;
        } else if (this.equals(INACTIVE)) {
            return ACTIVE;
        } else {
            throw new IllegalStateException(
                    "Enumeration value '" + super.toString() + "' has no reverse value");
        }
    }

    @Override
    public String toString() {
        return code;
    }

    public static SensorStatusEnum valueOf(long id) {

        SensorStatusEnum[] values = SensorStatusEnum.values();

        for (SensorStatusEnum value : values) {
            if (value.id == id) {
                return value;
            }
        }

        return null;
    }
}
