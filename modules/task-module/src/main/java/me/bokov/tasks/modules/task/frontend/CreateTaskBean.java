package me.bokov.tasks.modules.task.frontend;

import lombok.Getter;
import lombok.Setter;
import me.bokov.tasks.core.common.domain.TaskVO;
import me.bokov.tasks.core.common.domain.UserVO;
import me.bokov.tasks.core.service.TaskService;
import me.bokov.tasks.core.service.UserService;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIMessages;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
@Getter
@Setter
public class CreateTaskBean implements Serializable {

    @EJB
    private TaskService taskService;

    @EJB
    private UserService userService;

    private String summary;
    private String description;
    private Long selectedAssigneeId;
    private List<UserVO> selectableUsers = new ArrayList<> ();

    public void save () {

        TaskVO taskToCreate = new TaskVO ();

        taskToCreate.setSummary (getSummary ());
        taskToCreate.setDescription (getDescription ());
        taskToCreate.setStatus (TaskVO.Status.TODO);
        taskToCreate.setAssigneeId (selectedAssigneeId);

        taskService.createTask (taskToCreate);

        Faces.navigate ("saved");

    }

    @PostConstruct
    public void onInit () {

        selectableUsers.clear ();

        selectableUsers.addAll (
                userService.getAllUsers ()
        );

    }

}
