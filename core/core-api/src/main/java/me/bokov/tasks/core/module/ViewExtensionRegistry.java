package me.bokov.tasks.core.module;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@ApplicationScoped
@Named
public class ViewExtensionRegistry implements Serializable {

    private final Map <String, List <ViewExtension>> viewExtensions = Collections.synchronizedMap (new HashMap<> ());

    public synchronized List <ViewExtension> getViewExtensions (String extensionPoint) {
        return viewExtensions.getOrDefault (extensionPoint, Collections.emptyList ());
    }

    public synchronized void addViewExtension (String extensionPoint, ViewExtension viewExtension) {
        List <ViewExtension> list = new ArrayList<> (
                viewExtensions.getOrDefault (extensionPoint, Collections.emptyList ())
        );
        list.add (viewExtension);
        list.sort (Comparator.comparingInt (ViewExtension::getOrder));
        viewExtensions.put (extensionPoint, Collections.unmodifiableList (list));
    }

}
