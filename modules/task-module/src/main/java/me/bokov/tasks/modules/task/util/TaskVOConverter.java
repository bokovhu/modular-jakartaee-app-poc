package me.bokov.tasks.modules.task.util;

import me.bokov.tasks.core.common.domain.TaskVO;
import me.bokov.tasks.dal.entity.TaskEntity;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TaskVOConverter {

    public TaskVO taskEntityToVO (TaskEntity task) {

        TaskVO vo = new TaskVO ();

        vo.setId (task.getId ());
        vo.setAssigneeId (task.getAssignee ().getId ());
        vo.setSummary (task.getSummary ());
        vo.setDescription (task.getDescription ());
        vo.setStatus (task.getStatus ());

        return vo;

    }

}
