package me.bokov.tasks.modules.task;

import me.bokov.tasks.core.common.domain.TaskVO;
import me.bokov.tasks.core.common.event.TaskEvent;
import me.bokov.tasks.core.service.TaskService;
import me.bokov.tasks.dal.dao.TaskDao;
import me.bokov.tasks.dal.entity.TaskEntity;
import me.bokov.tasks.modules.task.util.TaskVOConverter;
import me.bokov.tasks.dal.dao.UserDao;

import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@Local (TaskService.class)
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class TaskServiceBean implements TaskService {

    @EJB
    private TaskDao taskDao;

    @EJB
    private UserDao userDao;

    @Inject
    private Event<TaskEvent> taskEvent;

    @Inject
    private TaskVOConverter taskVOConverter;

    @Override
    public TaskVO createTask (TaskVO task) {

        TaskEntity taskEntity = new TaskEntity ();

        if (task.getAssigneeId () != null) {
            taskEntity.setAssignee (userDao.findById (task.getAssigneeId ()));
        }

        taskEntity.setSummary (task.getSummary ());
        taskEntity.setDescription (task.getDescription ());
        taskEntity.setStatus (task.getStatus ());

        taskDao.create (taskEntity);

        return taskVOConverter.taskEntityToVO (taskEntity);
    }

    @Override
    public void updateTask (TaskVO task) {

        TaskEntity taskEntity = taskDao.findById (task.getId ());

        if (taskEntity != null) {

            if (task.getAssigneeId () != null) {
                taskEntity.setAssignee (userDao.findById (task.getAssigneeId ()));
            } else {
                taskEntity.setAssignee (null);
            }

            taskEntity.setSummary (task.getSummary ());
            taskEntity.setDescription (task.getDescription ());
            taskEntity.setStatus (task.getStatus ());

        }

    }

    @Override
    public void removeTask (TaskVO task) {

        TaskEntity taskEntity = taskDao.findById (task.getId ());

        if (taskEntity != null) {
            taskDao.delete (taskEntity);
        }

    }

    @Override
    public List<TaskVO> getAllTasks () {
        return taskDao.findAll ()
                .stream ()
                .map (taskVOConverter::taskEntityToVO)
                .collect (Collectors.toList ());
    }

}
