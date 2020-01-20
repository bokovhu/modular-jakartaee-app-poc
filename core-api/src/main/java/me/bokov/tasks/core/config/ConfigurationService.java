package me.bokov.tasks.core.config;

import javax.ejb.Local;

@Local
public interface ConfigurationService {

    Category getCategory (String name);
    void addCategory (String name);
    void removeCategory (String name);
    void setProperty (String categoryName, String propertyKey, String propertyValue);
    void removeProperty (String categoryName, String propertyKey);
    Property getProperty (String categoryName, String propertyKey);

}
