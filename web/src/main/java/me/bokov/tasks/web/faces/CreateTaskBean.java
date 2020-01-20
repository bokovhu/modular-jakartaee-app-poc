package me.bokov.tasks.web.faces;

import lombok.Getter;
import lombok.Setter;
import me.bokov.tasks.core.model.domain.Task;
import me.bokov.tasks.core.task.TaskService;
import org.primefaces.PrimeFaces;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
@Getter
@Setter
public class CreateTaskBean {

    @EJB
    private TaskService taskService;

    private String summary;
    private String description;

    public void createTask () {

        Task task = new Task ();
        task.setSummary (getSummary ());
        task.setDescription (getDescription ());

        taskService.createTask (task);

        PrimeFaces.current ()
                .ajax ()
                .update ("@id(taskListForm)");

        setSummary ("");
        setDescription ("");

    }

}
