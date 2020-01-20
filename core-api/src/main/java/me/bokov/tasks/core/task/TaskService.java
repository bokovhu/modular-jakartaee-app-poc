package me.bokov.tasks.core.task;

import me.bokov.tasks.core.model.domain.Task;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TaskService {

    Task createTask (Task task);
    void updateTask (Task task);
    void removeTask (Task task);
    List <Task> getAllTasks ();

}
