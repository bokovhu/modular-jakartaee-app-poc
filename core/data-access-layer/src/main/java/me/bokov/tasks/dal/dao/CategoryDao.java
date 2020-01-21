package me.bokov.tasks.dal.dao;

import me.bokov.tasks.dal.dao.AbstractGenericDao;
import me.bokov.tasks.dal.entity.CategoryEntity;

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
import java.util.ArrayList;
import java.util.List;

@LocalBean
@Stateless
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class CategoryDao extends AbstractGenericDao <CategoryEntity> {

    @Override
    protected Class<CategoryEntity> getEntityClass () {
        return CategoryEntity.class;
    }

    public CategoryEntity findByName (String name) {

        CriteriaBuilder cb = getEntityManager ().getCriteriaBuilder ();
        CriteriaQuery <CategoryEntity> cq = cb.createQuery (CategoryEntity.class);
        Root <CategoryEntity> root = cq.from (CategoryEntity.class);
        cq.where (
                cb.equal (
                        root.get ("name"),
                        name
                )
        );

        TypedQuery <CategoryEntity> tq = getEntityManager ().createQuery (cq);

        List <CategoryEntity> result =  tq.getResultList ();

        if (result.isEmpty ()) return null;
        return result.get (0);

    }

    public CategoryEntity findByNameOrCreateNew (String name) {

        CategoryEntity existing = findByName (name);
        if (existing != null) return existing;

        CategoryEntity categoryEntity = new CategoryEntity ();
        categoryEntity.setName (name);
        categoryEntity.setProperties (new ArrayList<> ());

        create (categoryEntity);

        return categoryEntity;

    }

}
