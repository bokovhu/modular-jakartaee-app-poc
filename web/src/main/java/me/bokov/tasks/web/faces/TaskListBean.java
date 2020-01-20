package me.bokov.tasks.web.faces;

import lombok.Getter;
import lombok.Setter;
import me.bokov.tasks.core.model.domain.Task;
import me.bokov.tasks.core.model.event.TaskEvent;
import me.bokov.tasks.core.task.TaskService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
@Getter
@Setter
public class TaskListBean implements Serializable{

    @EJB
    private TaskService taskService;

    private List <Task> tasks = new ArrayList<> ();

    @PostConstruct
    public void init () {

        populateTasks ();

    }

    private void populateTasks () {

        tasks.clear ();
        tasks.addAll (taskService.getAllTasks ());

    }

    public void onTaskEvent (@Observes TaskEvent taskEvent) {

        populateTasks ();

    }

}
