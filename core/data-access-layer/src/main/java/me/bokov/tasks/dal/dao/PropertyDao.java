package me.bokov.tasks.dal.dao;

import me.bokov.tasks.dal.dao.AbstractGenericDao;
import me.bokov.tasks.dal.entity.CategoryEntity;
import me.bokov.tasks.dal.entity.PropertyEntity;

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

@LocalBean
@Stateless
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class PropertyDao extends AbstractGenericDao <PropertyEntity> {

    @Override
    protected Class<PropertyEntity> getEntityClass () {
        return PropertyEntity.class;
    }

    public PropertyEntity findByCategoryAndKey (Long categoryId, String key) {

        CriteriaBuilder cb = getEntityManager ().getCriteriaBuilder ();
        CriteriaQuery <PropertyEntity> cq = cb.createQuery (PropertyEntity.class);
        Root <PropertyEntity> root = cq.from (PropertyEntity.class);
        cq.where (
                cb.and (
                        cb.equal (
                                root.get ("category").get ("id"),
                                categoryId
                        ),
                        cb.equal (
                                root.get ("key"),
                                key
                        )
                )
        );

        TypedQuery <PropertyEntity> tq = getEntityManager ().createQuery (cq);

        List<PropertyEntity> result = tq.getResultList ();

        if (result.isEmpty ()) return null;
        return result.get (0);

    }

    public PropertyEntity findByCategoryAndKeyOrCreateNew (Long categoryId, String key) {

        PropertyEntity existing = findByCategoryAndKey (categoryId, key);
        if (existing != null) return existing;

        PropertyEntity propertyEntity = new PropertyEntity ();
        propertyEntity.setKey (key);
        propertyEntity.setValue ("__unset__");
        propertyEntity.setCategory (getEntityManager ().find (CategoryEntity.class, categoryId));

        create (propertyEntity);

        return propertyEntity;

    }

}
