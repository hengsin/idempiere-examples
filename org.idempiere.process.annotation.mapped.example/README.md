Examples for using process, process parameter annotations, @Reference to inject IMappedProcessFactory and @Activate to start the scan of process annotation.

1. To Test:
* Import project to your iDempiere Eclipse workspace.
* Duplicate service.product launch configuration.
* At the duplicated service.product launch configuration, goes to the plugin tab and select this plugin.
* Launch iDempiere using the duplicated service.product launch configuration.
* The plugin include 2Pack that will create process and menu with the name "Print Business Partner XML".

2. POM Files
* pom.xml
	* Use repository in the `<repositories>` section of POM for resolution of iDempiere core and target platform bundles.
	* Parent pom is ../parent-repository-pom.xml.
* pom-targetplaform.xml
	* Use custom target platform definition (idempiere.core.repository.target) for resolution of iDempiere core and target platform bundles.
	* Parent pom is ../parent-targetplatform-pom.xml.
* pom-core-parent.xml
	* Use locally install iDempiere bundles for resolution of iDempiere core bundles.
	* Use iDempiere core target platform definition for resolution of target platform bundles.
	* Parent pom is idempiere/org.idempiere.parent/pom.xml.
	* Pre-requisite: run `mvn install` at idempiere source folder to install iDempiere core bundles into local repository.

3. Folder structure for pom.xml and pom-targetplaform.xml  
```
workspace folder  
|  
+--- idempiere  
|  
|    +--- org.idempiere.parent  
|  
+--- idempiere-examples  
|  
|    +--- org.idempiere.process.annotation.mapped.example  
```
