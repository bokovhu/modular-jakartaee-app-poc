package me.bokov.tasks.dal.dao;

import me.bokov.tasks.dal.dao.AbstractGenericDao;
import me.bokov.tasks.dal.entity.TaskEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class TaskDao extends AbstractGenericDao <TaskEntity> {

    @Override
    protected Class<TaskEntity> getEntityClass () {
        return TaskEntity.class;
    }

}
