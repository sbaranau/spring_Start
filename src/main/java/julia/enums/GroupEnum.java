package julia.enums;

import julia.entity.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum для работы с группами ролей.
 * @author Dmitry_Belyaev
 */
public enum GroupEnum {

    ADMINISTRATION(1L, "Администрация"),
    PRODUCTIVE_AREA(2L, "Производственный участок"),
    CHEMISTRY_DEPARTMENT(3L, "Химическое отделение"),
    MICROBIOLOGICAL_DEPARTMENT(4L, "Микробиологическое отделение"),
    ARCHIVE(5L, "Архив"),
    CHIEF_OKK(6L, "Руководитель ОКК"),
    HEAD_OKK(7L, "Руководитель ООК"),
    HEAD_DEPARTMENT(8L, "Руководитель подразделения");

    private static final Map<Long, Group> GROUP_MAP =
            new HashMap<>(GroupEnum.values().length);

    static {
        for (GroupEnum groupEnum : GroupEnum.values()) {
            Group group = new Group();
            group.setId(groupEnum.getId());
            group.setName(groupEnum.getGroupName());
            GROUP_MAP.put(group.getId(), group);
        }
    }

    private Long id;

    private String groupName;

    /**
     * Конструктор.
     * @param id идентификатор группы
     * @param group наименование группы
     */
    GroupEnum(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    /**
     * Получить объект Group.
     * @return объект Group
     */
    public Group getGroup() {
        return GROUP_MAP.get(this.id);
    }

    /**
     * Получает id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает groupName.
     * @return groupName
     */
    public String getGroupName() {
        return groupName;
    }
}
