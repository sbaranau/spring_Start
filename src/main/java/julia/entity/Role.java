package julia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Справочник ролей
 *
 * @author Ivan Shyrma
 */
public class Role extends NamedEntity {

    private static final long serialVersionUID = 1L;

    /**Наименование роли*/
    private String nameRu;

    /**Роль на чтение или на редактирование*/
    private Boolean read;

    /**Активна ли роль*/
    private Boolean active;

    /**Описание роли*/
    @JsonIgnore
    private String description;


    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
