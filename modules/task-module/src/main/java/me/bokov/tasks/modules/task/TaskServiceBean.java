package me.bokov.tasks.modules.task;

import me.bokov.tasks.core.model.domain.Task;
import me.bokov.tasks.core.model.event.TaskEvent;
import me.bokov.tasks.core.task.TaskService;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

@Singleton
@Local (TaskService.class)
@ConcurrencyManagement (ConcurrencyManagementType.CONTAINER)
public class TaskServiceBean implements TaskService {

    @Inject
    private Event <TaskEvent> taskEvent;

    private final Map <String, Task> tasks = new HashMap<> ();

    @Override
    public Task createTask (Task task) {

        task.setId (UUID.randomUUID ().toString ());
        tasks.put (task.getId (), task);

        taskEvent.fire (
                new TaskEvent (
                        TaskEvent.Action.CREATED,
                        task.getId (),
                        task,
                        LocalDateTime.now ()
                )
        );

        return task;
    }

    @Override
    public void updateTask (Task task) {

        tasks.put (task.getId (), task);

        taskEvent.fire (
                new TaskEvent (
                        TaskEvent.Action.UPDATED,
                        task.getId (),
                        task,
                        LocalDateTime.now ()
                )
        );

    }

    @Override
    public void removeTask (Task task) {

        tasks.remove (task.getId ());

        taskEvent.fire (
                new TaskEvent (
                        TaskEvent.Action.DELETED,
                        task.getId (),
                        null,
                        LocalDateTime.now ()
                )
        );

    }

    @Override
    public List<Task> getAllTasks () {
        return new ArrayList<> (tasks.values ());
    }

}
