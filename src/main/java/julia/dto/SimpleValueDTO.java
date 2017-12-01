package julia.dto;

/**
 * Объект для передачи простых типов данных
 * @author Pudul Yuriy
 */
public class SimpleValueDTO<T> {

    private T value;

    public SimpleValueDTO(T value) {
        this.value = value;
    }

    public SimpleValueDTO() {}

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
