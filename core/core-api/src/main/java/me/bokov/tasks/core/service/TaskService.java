package me.bokov.tasks.core.service;


import me.bokov.tasks.core.common.domain.TaskVO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TaskService {

    TaskVO createTask (TaskVO task);
    void updateTask (TaskVO task);
    void removeTask (TaskVO task);
    List <TaskVO> getAllTasks ();

}
