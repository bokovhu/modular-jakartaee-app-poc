package me.bokov.tasks.modules.config;

import me.bokov.tasks.core.common.config.Category;
import me.bokov.tasks.core.common.config.Property;
import me.bokov.tasks.core.service.ConfigurationService;
import me.bokov.tasks.dal.dao.CategoryDao;
import me.bokov.tasks.dal.dao.PropertyDao;
import me.bokov.tasks.dal.entity.CategoryEntity;
import me.bokov.tasks.dal.entity.PropertyEntity;

import javax.ejb.*;
import java.util.HashMap;
import java.util.Map;

@Stateless
@Local (ConfigurationService.class)
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class ConfigurationServiceBean implements ConfigurationService {

    @EJB
    private CategoryDao categoryDao;

    @EJB
    private PropertyDao propertyDao;

    @Override
    public Category getCategory (String name) {

        CategoryEntity categoryEntity = categoryDao.findByName (name);
        if (categoryEntity == null) return null;

        Map <String, Property> categoryPropertyMap = new HashMap<> ();
        for (PropertyEntity propertyEntity : categoryEntity.getProperties ()) {
            categoryPropertyMap.put (
                    propertyEntity.getKey (),
                    new Property (
                            propertyEntity.getKey (),
                            propertyEntity.getValue ()
                    )
            );
        }

        return new Category (categoryEntity.getName (), categoryPropertyMap);
    }

    @Override
    public void addCategory (String name) {

        CategoryEntity categoryEntity = new CategoryEntity ();
        categoryEntity.setName (name);
        categoryDao.create (categoryEntity);

    }

    @Override
    public void removeCategory (String name) {

        CategoryEntity categoryEntity = categoryDao.findByName (name);
        if (categoryEntity != null) {
            categoryDao.delete (categoryEntity);
        }

    }

    @Override
    public void setProperty (String categoryName, String propertyKey, String propertyValue) {

        CategoryEntity categoryEntity = categoryDao.findByNameOrCreateNew (categoryName);
        PropertyEntity propertyEntity = propertyDao.findByCategoryAndKeyOrCreateNew (
                categoryEntity.getId (),
                propertyKey
        );

        propertyEntity.setValue (propertyValue);

    }

    @Override
    public void removeProperty (String categoryName, String propertyKey) {

        CategoryEntity categoryEntity = categoryDao.findByName (categoryName);
        if (categoryEntity != null) {

            PropertyEntity propertyEntity = propertyDao.findByCategoryAndKey (
                    categoryEntity.getId (),
                    propertyKey
            );
            if (propertyEntity != null) {
                propertyDao.delete (propertyEntity);
            }

        }

    }

    @Override
    public Property getProperty (String categoryName, String propertyKey) {


        CategoryEntity categoryEntity = categoryDao.findByName (categoryName);
        if (categoryEntity != null) {

            PropertyEntity propertyEntity = propertyDao.findByCategoryAndKey (
                    categoryEntity.getId (),
                    propertyKey
            );

            if (propertyEntity != null) {
                return new Property (propertyEntity.getKey (), propertyEntity.getValue ());
            }

        }

        return null;
    }

}
