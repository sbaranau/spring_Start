package julia.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan Shyrma
 */
public interface Dao<T extends Serializable, K extends Serializable> {

    /**
     * @param newInstance new instance
     */
    void create(T newInstance);

    /**
     * Read instance by key
     * @param key key
     * @return instance
     */
    T read(K key);

    /**
     * Update instance
     * @param instance instance
     */
    void update(T instance);

    /**
     * Delete instance by key
     * @param key key
     */
    void delete(K key);

    /**
     * Find all instances
     * @return list of instances
     */
    List<T> findAll();

}
