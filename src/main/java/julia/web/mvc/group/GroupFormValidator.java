package julia.web.mvc.group;

import julia.entity.Group;
import julia.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Nikolai_Tarasevich
 *
 */
@Component
public class GroupFormValidator implements Validator {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GroupForm groupForm = (GroupForm) target;
        Group gr = groupService.findGroupByName(groupForm.getGroup().getName());
        if (gr != null && groupForm.getGroup().getId() != gr.getId()) {
            errors.rejectValue("group.name", "settings.group.already.exist");
        }
        if (groupForm.getGroup().getName() == null || groupForm.getGroup().getName().isEmpty()) {
            errors.rejectValue("group.name", "settings.group.null.name");
        }
    }

}
