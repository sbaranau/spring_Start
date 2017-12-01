package julia.dto.group;

import julia.entity.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DTO ролей для групп
 *
 * @author Pudul Yuriy
 */
public class RolesForGroupDTO {

    private static final String EDIT = "_EDIT";

    /**Наименование роли*/
    private String name;

    /**Список ролей*/
    private List<Role> roles = new ArrayList<>();

    public static List<RolesForGroupDTO> convertToMap(final List<Role> roles) {
        return convertToMap(roles, Collections.<Long>emptyList());
    }

    /**
     * Формирует список ролей для группы вида {nameOfRole, roleList{editRole, readRole}}
     *
     * @param roles список ролей
     * @param activeRoles список принадлижащих группе ролей
     * @return список RolesForGroupDTO
     */
    public static List<RolesForGroupDTO> convertToMap(final List<Role> roles, final List<Long> activeRoles) {
        final List<RolesForGroupDTO> result = new ArrayList<>();
        final List<String> namesWhichResultListContains = new ArrayList<>(roles.size());

        for (final Role role : roles) {
            final RolesForGroupDTO roleForResult = new RolesForGroupDTO();
            final String roleName = role.getNameRu();

            role.setRead(!role.getName().contains(EDIT));
            role.setActive(activeRoles.contains(role.getId()));

            if (!namesWhichResultListContains.contains(roleName)) {
                namesWhichResultListContains.add(roleName);
                roleForResult.setName(roleName);
                roleForResult.getRoles().add(role);
                result.add(roleForResult);
            } else {
                for (final RolesForGroupDTO resultRole : result) {
                    if (resultRole.getName().equals(roleName)) {
                        resultRole.getRoles().add(role);
                    }
                }
            }
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
