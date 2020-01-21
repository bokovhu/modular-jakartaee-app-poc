package me.bokov.tasks.dal.dao;

import me.bokov.tasks.dal.entity.AbstractBaseEntity;

import java.util.List;

public interface GenericDao <E extends AbstractBaseEntity> {

    void create (E entity);
    void delete (E entity);
    List <E> findAll ();
    E findById (Long id);

}
