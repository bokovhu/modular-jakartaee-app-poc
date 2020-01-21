package me.bokov.tasks.dal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.bokov.tasks.core.common.domain.TaskVO;
import me.bokov.tasks.dal.entity.AbstractBaseEntity;
import me.bokov.tasks.dal.entity.UserEntity;

import javax.persistence.*;

@EqualsAndHashCode (callSuper = true)
@Data
@Entity
@Table (name = "tasks")
public class TaskEntity extends AbstractBaseEntity {

    @Enumerated (EnumType.STRING)
    @Column (name = "task_status", nullable = false)
    private TaskVO.Status status;

    @Column (name = "summary", nullable = false)
    private String summary;

    @Column (name = "description", nullable = false, length = 65535)
    private String description;

    @ManyToOne (fetch = FetchType.LAZY, optional = true)
    @JoinColumn (name = "assignee_id", nullable = true)
    private UserEntity assignee;

}
