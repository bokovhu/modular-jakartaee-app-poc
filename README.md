# Modular Jakarta EE Application (Proof of Concept)

This repository contains code for a proof of concept demonstrating the capabilities of the Jakarta EE platform, when it
comes to building modular applications. The purpose of the project was to investigate whether it's possible to create
an architecture, that supports splitting the code into multiple, small modules. These modules maybe included or excluded
from the application in runtime, on-demand. This means, that in order to include or exclude functionality from the 
system, a full rebuild of the software is not required, instead you could remove or add the module bundle files to the
application server, redeploy the application to modify the feature set of the app.

This is made possible thanks to the way class loading and resource management works in the Jakarta EE 8 platform. The
application is bundled as a single WAR file (the `web` maven module). This maven module depends on all required module
libraries, that are placed in the `WEB-INF/lib` directory of the generated WAR file. JAR archives placed in this 
directory can expose resources, JSF facelets, CDI beans, JPA entities, and configuration to the deployed application. 

The project consists of the following modules:

* `core`: This module contains the most basic functionality that other modules may depend on, in order to be part of
the application. This includes the most basic JPA entities the application modules may need to work with (including the
applications "main" persistence unit), some CDI and EJB beans that help with module registration, as well as service
interfaces, and the domain model. The `core` module has the following sub modules:
    * `common-model`: The `common-model` maven module has no other dependencies in the application. It contains the
    most basic domain model classes, including common event data classes, SOA request/response classes, and value 
    objects representing the state of entities to the presentation layer
    * `data-access-layer`: This module contains the `persistence.xml` file describing the "main" persistence unit of 
    the application, as well as the DAO interfaces and implementations other modules may use in order to access data
    stored in the application
    * `core-api`: This module contains the service interfaces that are part of the application, as well as beans that
    help with module registration
* `language-packs`: The submodules of this maven module represent a single supported language, and contain the resource
bundles and `faces-config.xml` files in order to support that given language. A new language may be added to a deployed
application via putting a new language pack JAR file into the `WEB-INF/lib` directory of the WAR file, or using my 
`ext-jars` WildFly subsystem, into a directory outside the WAR file
* `modules`: The submodules of this maven module contain small pieces of the application logic. These modules may 
contain parts of the UI (JSF facelets, resources) as well as parts of the business layer. If a given module requires 
extra entities, that are not part of the `data-access-layer`, it may even provide a new persistence unit to store its
own data. If these entities need to reference entities in other modules, or the `data-access-layer`, the external 
entities must be registered via `<class>` entries in the `persistence.xml` deployment descriptor.
* `web`: This maven module glues the `core`, and the most basic application modules together, to create a deployable 
WAR file. It also contains the basic servlet and JSF configuration files.

This architecture supports not only the exclusion or inclusion of application modules in deployed instances, but also
the development of extra module bundles by developers not part of the original application development team. This was
also a scope of the proof of concept.