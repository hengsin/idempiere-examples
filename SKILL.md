---
name: idempiere-bundle-from-template
description: Orchestrates the creation of new iDempiere OSGi bundle project by using an existing example project as a template
---
# idempiere-bundle-from-template Skill

This skill allows you to create a new iDempiere OSGi bundle project by using an existing example project as a template. It automates the initial setup steps, making it easier to start new iDempiere development projects.

## Description

This skill facilitates the rapid creation of new iDempiere plugins by leveraging the provided example projects. It handles file copying, renaming, and initial configuration updates in `pom.xml`, `META-INF/MANIFEST.MF`, and `OSGI-INF/*.xml` files.

## Available Templates

The following example projects can be used as templates:

*   **org.idempiere.callout.annotation.example**: Example to have plugin column callout executed after core column callouts using annotations.
*   **org.idempiere.event.annotation.example**: Examples for using event annotations with event delegate.
*   **org.idempiere.eventhandler.example**: Example of using `AbstractEventHandler` (OSGi EventAdmin) to handle model events (e.g., Business Partner events).
*   **org.idempiere.framework.listener.example**: Example of OSGi framework listener (BundleListener, FrameworkListener).
*   **org.idempiere.listbox.group.example**: Example for `AbstractGroupListitemRenderer` and `ListitemGroup` in ZK.
*   **org.idempiere.process.annotation.example**: Examples for using process, process parameter annotations and `AnnotationBasedProcessFactory`.
*   **org.idempiere.process.annotation.mapped.example**: Examples for using process, process parameter annotations, and `IMappedProcessFactory`.
*   **org.idempiere.rest.resource.ext.example**: Example of extending iDempiere REST resources (e.g., adding a Tax resource).
*   **org.idempiere.test.example**: Examples for unit testing in iDempiere.
*   **org.idempiere.window.validator.example**: Example project to illustrate the usage of `WindowValidator` API.
*   **org.idempiere.wlistbox.editor.example**: Implement custom Payment Allocation form to illustrate how to setup custom column editor for `WListbox`.
*   **org.idempiere.zul.form.example**: Examples for using ZUL and ZK annotations to build custom forms.
*   **org.idempiere.plugin.example**: Empty plugin

## Parameters

This skill requires the following parameters:

*   `template_project_name` (string, required): The name of the example project directory to use as a template (e.g., `org.idempiere.callout.annotation.example`). This should be one of the example project names found in the `idempiere-examples` directory.
*   `new_project_name` (string, required): The desired name for your new project directory and Maven artifactId (e.g., `org.mycompany.mycallout`).
*   `new_base_package` (string, required): The desired new base Java package structure for your project (e.g., `org.mycompany.mycallout`). **DEFAULT: Use the value of `new_project_name`.**. This will be used to rename the source directories and update package declarations.
*   `new_bundle_name` (string, optional): The human-readable name for the OSGi bundle (e.g., `My Company Callout Example`). If not provided, a default name will be derived from `new_project_name`.
*   `new_bundle_version` (string, optional): The version of the OSGi bundle (e.g., `1.0.0.SNAPSHOT`). Defaults to `1.0.0.SNAPSHOT`.
*   `new_group_id` (string, optional): The Maven group ID for the new project (e.g., `org.mycompany`). Defaults to `org.idempiere`.
*   `component_class_name` (string, optional): For OSGi service components, the main component class name (e.g., `MyCalloutFactory`). This is used to update references in `OSGI-INF/*.xml` files.
*   `parent_group_id` (string, optional): The Maven group ID of the parent artifact. Defaults to the value of `new_group_id`
*   `parent_artifact_id` (String, optional): The Maven artifact ID of the parent artifact. Default to the value of 'new_project_name'+".parent".
*   `relative_path_of_parent_pom` (String, optional):  Relative path to parent pom.

## Instructions

To use this skill, follow these steps:

1.  ** Select and Confirm Template:**
    * Identify the template_project_name from the user's request.
    * STOP: You must output the following message to the user and wait for their reply before proceeding: "I have selected the template [template_project_name] for your new project [new_project_name]. Shall I proceed with this template?".
    * If the user suggests a different template, update the template_project_name and repeat this confirmation.
    * Do not proceed to Step 2 until the user explicitly confirms.
2 **Copy the Template Project:**
    *   Copy the entire directory of the `template_project_name` to a new directory named `new_project_name`.
    `cp -R idempiere-examples/<template_project_name> <new_project_name>`
    *   Copy parent-repository-pom.xml to the parent folder of the just created new directory.

3.  **Update `pom.xml`:**
    Modify the `pom.xml` file in `<new_project_name>/pom.xml` to update the following:
    *   `<artifactId>`: Set to `new_project_name`.
    *   `<groupId>`: Set to `new_group_id` (default `org.idempiere`).
    *   `<name>`: Set to `new_bundle_name`.
    *   `<version>`: Set to `new_bundle_version` (default `1.0.0.SNAPSHOT`).
    *   Remove `<parent>` section if `relative_path_of_parent_pom` is not available.
    *   `<groupId>` within `<parent>`: Set to `parent_group_id`
    *   `<artifactId>` within `<parent>`: Set to `parent_artifact_id`
    *   `<relativePath>` within `<parent>`: Set to `relative_path_of_parent_pom`

4.  **Update `parent-repository-pom.xml`:**
    Modify the `parent-repository-pom.xml` file in parent folder of `<new_project_name>` to update the following:   
    *   `<groupId>`: Set to `parent_group_id`
    *   `<artifactId>`: Set to `parent_artifact_id`

5.  **Update `META-INF/MANIFEST.MF`:**
    Modify the `META-INF/MANIFEST.MF` file in `<new_project_name>/META-INF/MANIFEST.MF` to update the following:
    *   `Bundle-SymbolicName`: Set to `new_project_name`.
    *   `Bundle-Name`: Set to `new_bundle_name`.
    *   `Export-Package`: Update the package paths to reflect `new_base_package`.
    *   `Bundle-Version`: Set to `new_bundle_version`.

6.  **Rename Source Package Directories:**
    Recursively rename the Java source package directories under `<new_project_name>/src` to match `new_base_package`. This typically involves moving `org/idempiere/<template_specific_part>/example` to `new/base/package/structure`.
    Example: `mv <new_project_name>/src/org/idempiere/callout/annotation/example <new_project_name>/src/org/mycompany/mycallout`

7.  **Update Java Source Files:**
    Go through all `.java` files in the new source directory (`<new_project_name>/src/`) and:
    *   Update `package` declarations at the top of each file to `new_base_package`.
    *   Update any internal `import` statements that refer to the old package structure.

8.  **Update OSGI-INF XML Files (if applicable):**
    If the template project contains `OSGI-INF/*.xml` files (e.g., for service components, event handlers, callout factories), modify these files:
    *   Update the `class` attribute in the `<component>` tag to reflect the new package and class name (e.g., `class="new_base_package.MyNewComponentName"`).
    *   Update any references to specific old class names to `component_class_name`.

9.  **Review and Clean Up:**
    *   Review all changes to ensure consistency.
    *   Remove any example-specific code or comments that are not relevant to your new project.
    *   Prompt user to confirm the run of `mvn clean verify` within the new project directory to build and verify.

This skill provides a structured approach to creating new iDempiere plugin, minimizing repetitive manual tasks and ensuring compliance with project conventions.

## Example Usage:

Let's say you want to create a new callout annotation bundle from `org.idempiere.callout.annotation.example`.

`activate_skill("idempiere-bundle-from-template", template_project_name="org.idempiere.callout.annotation.example", new_project_name="org.mycompany.mycallout", new_base_package="org.mycompany.mycallout", new_bundle_name="My Company Callout Bundle", component_class_name="MyNewCalloutFactory")`
