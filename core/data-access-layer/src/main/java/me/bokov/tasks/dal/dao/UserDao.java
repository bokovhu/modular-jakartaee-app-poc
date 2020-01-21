package me.bokov.tasks.dal.dao;

import me.bokov.tasks.dal.dao.AbstractGenericDao;
import me.bokov.tasks.dal.entity.UserEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@LocalBean
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class UserDao extends AbstractGenericDao <UserEntity> {

    @Override
    protected Class<UserEntity> getEntityClass () {
        return UserEntity.class;
    }

    public UserEntity findByLoginName (String loginName) {

        CriteriaBuilder cb = getEntityManager ().getCriteriaBuilder ();
        CriteriaQuery <UserEntity> cq = cb.createQuery (UserEntity.class);
        Root <UserEntity> root = cq.from (UserEntity.class);
        cq.where (
                cb.equal (
                        root.get ("loginName"),
                        loginName
                )
        );

        TypedQuery <UserEntity> tq = getEntityManager ().createQuery (cq);

        List<UserEntity> result = tq.getResultList ();

        if (result.isEmpty ()) return null;
        return result.get (0);

    }

}
