package julia.dao;

import julia.entity.Data;

/**
 * @author Ivan Shyrma
 */
public interface DataDao extends Dao<Data, Long> {

    Data readBySensorId(Long id);

    void updateBySensorId(Data data);

    /**
     * Прочитать имя данных по идентификатору
     * @param id идентификатор данных
     * @return имя данных
     */
    String readDataNameById(Long id);

}
