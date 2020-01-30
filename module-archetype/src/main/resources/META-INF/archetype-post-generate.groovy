dir = new File(request.outputDirectory, request.artifactId)
src = new File(dir, 'src')
srcMain = new File(src, 'main')
srcJava = new File(srcMain, 'java')
srcResources = new File(srcMain, 'resources')
props = request.properties
moduleName = request.artifactId
if (moduleName.endsWith('-module')) {
    moduleName = moduleName.substring(0, moduleName.length () - '-module'.length())
}
moduleNameSplitted = moduleName.split('-')
moduleNameCamelCaseBuilder = new StringBuilder()
for (String part : moduleNameSplitted) {
    moduleNameCamelCaseBuilder.append(part.substring(0, 1).toUpperCase())
            .append(part.substring(1).toLowerCase());
}
moduleNameCamelCase = moduleNameCamelCaseBuilder.toString()

modulePackage = request.package
modulePackageSplitted = modulePackage.split ('\\.')

srcModulePackage = srcJava
for (String pkg : modulePackageSplitted) {
    srcModulePackage = new File (srcModulePackage, pkg)
}

moduleJava = new File (srcModulePackage, moduleNameCamelCase + 'Module.java')
moduleJava.write (
        'package ' + modulePackage + '\n'
        + 'public class ' + moduleNameCamelCase + 'Module {}'
)

moduleInitializerJava = new File (srcModulePackage, moduleNameCamelCase + 'ModuleInitializer.java')
moduleInitializerJava.write (
        'package ' + modulePackage + '\n'
                + 'public class ' + moduleNameCamelCase + 'ModuleInitializer {}'
)