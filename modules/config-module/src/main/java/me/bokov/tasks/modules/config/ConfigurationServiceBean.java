package me.bokov.tasks.modules.config;

import me.bokov.tasks.core.config.Category;
import me.bokov.tasks.core.config.ConfigurationService;
import me.bokov.tasks.core.config.Property;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Local (ConfigurationService.class)
@ConcurrencyManagement (ConcurrencyManagementType.CONTAINER)
public class ConfigurationServiceBean implements ConfigurationService {

    private Map <String, Map <String, String>> configuration = new HashMap<> ();

    @Override
    @Lock (LockType.READ)
    public Category getCategory (String name) {

        Map <String, String> propertiesMap = configuration.get (name);

        if (propertiesMap == null) return null;

        Map <String, Property> properties = new HashMap<> ();
        for (Map.Entry <String, String> propertyEntry : propertiesMap.entrySet ()) {
            properties.put (
                    propertyEntry.getKey (),
                    new Property (
                            propertyEntry.getKey (),
                            propertyEntry.getValue ()
                    )
            );
        }

        return new Category (name, properties);
    }

    @Override
    @Lock (LockType.WRITE)
    public void addCategory (String name) {

        configuration.put (name, new HashMap<> ());

    }

    @Override
    @Lock (LockType.WRITE)
    public void removeCategory (String name) {

        configuration.remove (name);

    }

    @Override
    @Lock (LockType.WRITE)
    public void setProperty (String categoryName, String propertyKey, String propertyValue) {

        Map <String, String> categoryProperties = configuration.get (categoryName);
        if (categoryProperties == null) categoryProperties = new HashMap<> ();

        categoryProperties.put (propertyKey, propertyValue);

        configuration.put (categoryName, categoryProperties);

    }

    @Override
    @Lock (LockType.WRITE)
    public void removeProperty (String categoryName, String propertyKey) {

        Map <String, String> categoryProperties = configuration.get (categoryName);
        if (categoryProperties == null) return;

        categoryProperties.remove (propertyKey);

        configuration.put (categoryName, categoryProperties);

    }

    @Override
    @Lock (LockType.READ)
    public Property getProperty (String categoryName, String propertyKey) {

        Map <String, String> categoryProperties = configuration.get (categoryName);
        if (categoryProperties == null) return null;

        String entryValue = categoryProperties.get (propertyKey);
        if (entryValue == null) return null;

        return new Property (propertyKey, entryValue);
    }

}
