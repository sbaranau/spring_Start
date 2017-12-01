package julia.dto;

/**
 * Объект для передачи простых объектов вида ключ значение
 * @author Pudul Yuriy
 */
public class SimpleObjectDTO<K,V> {

    private K key;
    private V value;

    public SimpleObjectDTO(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
