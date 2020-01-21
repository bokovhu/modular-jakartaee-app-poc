package me.bokov.tasks.dal.dao;

import me.bokov.tasks.dal.entity.AbstractBaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractGenericDao <E extends AbstractBaseEntity> implements GenericDao <E> {

    @PersistenceContext (unitName = "TasksPU")
    private EntityManager entityManager;

    protected EntityManager getEntityManager () {
        return entityManager;
    }

    protected abstract Class <E> getEntityClass ();

    @Override
    public void create (E entity) {
        getEntityManager ().persist (entity);
    }

    @Override
    public void delete (E entity) {
        getEntityManager ().remove (entity);
    }

    @Override
    public List<E> findAll () {
        return getEntityManager ().createQuery (
                "SELECT e FROM " + getEntityClass ().getSimpleName () + " e",
                getEntityClass ()
        ).getResultList ();
    }

    @Override
    public E findById (Long id) {
        return getEntityManager ().find (getEntityClass (), id);
    }

}
