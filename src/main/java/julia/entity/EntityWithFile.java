package julia.entity;

/**
 * Сущность с информацией о файле
 * @author vandronov
 */
public class EntityWithFile extends NamedEntity {

    /**
     * Идентификатор файла
     */
    private Long dataId;

    /**
     * Имя файла
     */
    private String dataName;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

}
