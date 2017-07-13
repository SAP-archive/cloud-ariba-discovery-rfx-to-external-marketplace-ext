# Ariba Public Sourcing

## Content:

- [Overview](#overview)
- [Technical Details](#technical_details)
- [Prerequisites](#prerequisites)
- [Register a Discovery RFX Publication to External Marketplace Application](#setup_ariba)
- [Build and Deploy the Application on SAP Cloud Platform](#build_deploy)
	- [Using the SAP Cloud Platform Cockpit](#build_deploy_cockpit)
	- [Using the Eclipse IDE](#build_deploy_eclipse)
- [Start the Public Sourcing Application](#start)
- [Notes On Retrieving Sourcing Events](#notes)
- [Additional Information](#additional_information)
	- [Resources](#additional_information_resources)
	- [License](#additional_information_license)

<a name="overview"/>

## Overview

Ariba Public Sourcing is a sample extension application for [Ariba Network](https://www.ariba.com/) that runs on [SAP Cloud Platform](https://cloudplatform.sap.com/). The purpose of the application is to collect public sourcing events from Ariba Discovery via [SAP Ariba Open APIs](https://developer.ariba.com/api) and to display them in an application running on SAP Cloud Platform.

The application uses the Discovery RFX Publication to External Marketplace API. You can run it either on enterprise, or trial [SAP Cloud Platform account](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/8ed4a705efa0431b910056c0acdbf377.html) in the [Neo environment](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/1a8ee4e7b27d4293af175f021db8ad9c.html).

These are the SAP Cloud Platform services and features in use:
* [Connectivity service](https://help.hana.ondemand.com/help/frameset.htm?e54cc8fbbb571014beb5caaf6aa31280.html) - the application uses the Connectivity service to obtain connection to SAP Ariba Open APIs.
* [SAP HANA / SAP ASE service](https://help.sap.com/viewer/d4790b2de2f4429db6f3dff54e4d7b3a/Cloud/en-US/f6567e3b7334403b9b275426fbe4fb04.html)

<a name="technical_details"/>

## Technical Details

The Ariba Public Sourcing extension is a Java application that calls Ariba's Discovery RFX Publication to External Marketplace API and fetches all available public sourcing events. The events are persisted in a database and displayed in a SAPUI5 front-end.
The events could be either fetched manually through the UI or automatically with a scheduler.

To use this extension application, you need to:

1. Register an SAP Ariba Open APIs application in [Ariba Developer Portal](https://developer.ariba.com/api).
2. Promote your registered application for production access. (optional, not required for working against SAP Ariba Open APIs sandbox environment)
3. Build and deploy the Java extension application on SAP Cloud Platform.
4. Configure the Java application connectivity.
5. Start the Java extension application.

<a name="prerequisites"/>

## Prerequisites

You need to:
* have an account for [SAP Ariba Developer Portal](https://developer.ariba.com/api)
* have an [SAP Cloud Platform trial account](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/65d74d39cb3a4bf8910cd36ec54d2b99.html)
* download or clone the project with Git
* have set up [Maven 3.0.x](http://maven.apache.org/docs/3.0.5/release-notes.html)

<a name="setup_ariba"/>

## Register a Discovery RFX Publication to External Marketplace Application in SAP Ariba Developer Portal

You already have an account for Ariba Developer Portal. Open the [guide](https://developer.ariba.com/api/guides) and follow the steps to register a new SAP Ariba Open APIs application. that will be used against SAP Ariba Open APIs sandbox environment.
At the end, you should have an application key related to the SAP Ariba Open APIs application. You will need it in order to call the Discovery RFX Publication to External Marketplace API, the sandbox environment.

> *Production access:*
When you want to work against production environment you should first [promote your application for production access](https://developer.ariba.com/api/guides).
At the end, you will have an API key and OAuth client related to the registered SAP Ariba Open APIs application. You  need them in order to call the Discovery RFX Publication to External Marketplace API production environment.


<a name="build_deploy"/>

## Build and Deploy the Application on SAP Cloud Platform

You have already downloaded or cloned the Public Sourcing extension application. Now you have to build the application and deploy it on the SAP Cloud Platform. To do that, use one of the following tools:

* SAP Cloud Platform Cockpit
* Eclipse IDE

<a name="build_deploy_cockpit"/>

### Using the SAP Cloud Platform Cockpit

#### Build the Application

1. Go to the `cloud-ariba-public-sourcing-ext` folder.
2. Build the project with:

        mvn clean install

The generated WAR file `ROOT.war` under target sub-folder `cloud-ariba-discovery-rfx-to-external-marketplace-ext\target` is ready to be deployed.

#### Deploy the Application Using the Cockpit

You have to [deploy](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/abded969628240259d486c4b29b3948c.html) the `ROOT.war` file as a Java application via SAP Cloud Platform Cockpit. Use Java Web Tomcat 8 as a runtime option.

<a name="build_deploy_eclipse"/>

### Using the Eclipse IDE

When using the Eclipse IDE, you can take a look at the structure and code of the application. You have to import the application as an existing Maven project. You also have to choose Java Web Tomcat 8 as a runtime option.

#### Prerequisites

* [JDK 8 or later](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Eclipse IDE for Java EE Developers](https://eclipse.org/downloads/)
* [SAP Cloud Platform Tools](https://tools.hana.ondemand.com/#cloud) ([Installing the SDK for Java Development](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/7613843c711e1014839a8273b0e91070.html))

#### Build the Application from Eclipse

1. You have to clone the `cloud-ariba-discovery-rfx-to-external-marketplace-ext` project.
      1. Оpen the _Git_ Perspective. Choose _Windows_ > _Open Perspective_ > _Other_. Select _Git_ and choose _OK_.
      2. Choose _Clone a Git repository_.
      3. Enter `https://github.com/SAP/cloud-ariba-discovery-rfx-to-external-marketplace-ext.git` in the _URI_ field and choose _Next_.
      4. Set the _Directory_ field and choose _Finish_.

2. You have to import the `cloud-ariba-discovery-rfx-to-external-marketplace-ext` project as an existing Maven project and then build it.
      1. In the _Java EE_ perspective, choose _File_ > _Import_ > _Maven_ > _Existing Maven Project_.
      2. Browse and select the folder where you have cloned the Git repository and choose _Finish_. Wait for the project to load.
      3. From the project context menu, choose _Run As_ > _Maven Build_.
      4. Enter `clean install` in the _Goals_ field and choose _Run_.
         The build should pass successfully.

#### Deploy the Application from Eclipse

To deploy the application from Eclipse IDE, follow these steps:

1. Set the context path to `/`
	1. In the _Project Explorer_ view right-click on the project and choose _Properties_ > _Web Project Settings_.
	2. For `Context root` enter `/`
2. In the _Servers_ view right-click on the white field and choose _New_ > _Server_.
3. Choose if you want to work locally or directly on SAP Cloud Platform
	1. Local installation
		1. Select _Java Web Tomcat 8_ and choose _Next_.
		2. Browse to the location of your _Java Web Tomcat 8_ SDK and choose _Next_.
	2. SAP Cloud Platform
		1. Select _SAP Cloud Platform_ and choose _Next_.
		2. Select _Java Web Tomcat 8_ as a runtime option.
5. Add the application to the _Configured_ field and choose _Finish_.

<a name="configure"/>

## Create Destination(s)


You need to create HTTP destination(s) on the SAP Cloud Platform:
* [Using Eclipse](https://help.sap.com/viewer/cca91383641e40ffbe03bdc78f00f681/Cloud/en-US/e520383cbb571014858bc5d52295f433.html)
* [Using SAP Cloud Platform cockpit](https://help.sap.com/viewer/cca91383641e40ffbe03bdc78f00f681/Cloud/en-US/e520383cbb571014858bc5d52295f433.html)

##### SAP Ariba Open APIs Destination

   Use the following required properties:

                Type: HTTP
                Name: ariba-public-sourcing
                URL: <SAP Ariba OpenAPIs Environment Url>
                Authentication: NoAuthentication

   And add four additional properties:

                JobIntervalInSeconds: <How often will the Discovery RFX Publication to External Marketplace API be called>
                SiteId: <The unique site id. Enter random string in case of sandbox environment>
                ApiKey: <SAP Ariba Open APIs application API key>

##### SAP Ariba Open APIs OAuth Server Destination
(skip this step if you are working against SAP Ariba Open APIs sandbox environment)

   Use the following required properties:

                Type: HTTP
                Name: ariba-open-apis-oauth-server
                URL: <SAP Ariba OpenAPIs OAuth Server URL e.g. https://api.ariba.com/v2/oauth/token>
                Authentication: BasicAuthentication
                User=<SAP Ariba OpenAPIs registered application OAuth Client ID>
                Password=<SAP Ariba Open APIs registered application OAuth Client Secret>

>Note: There are sample destinations in the project's resources folder.

<a name="start"/>

## Start the Application

After creating the destination, if you are working against SAP Cloud Platform [start (or restart in case the application is already started) the application via the cloud cockpit](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/7612f03c711e1014839a8273b0e91070.html).

<a name="notes"/>

## Notes On Retrieving Sourcing Events

The automatic retrieving of sourcing events from Ariba is commented out in the source code of the Java application and the retrieving is done manually through the UI. If you prefer, you could turn on the automatic retrieving by uncommenting the related logic in PublicSourcingContextListener.java.

<a name="additional_information"/>

## Additional Information

<a name="additional_information_resources"/>

### Resources
* SAP Cloud Documentation - https://help.sap.com/viewer/product/CP/Cloud/en-US
* SAP Ariba Open APIs - https://developer.ariba.com/api

<a name="additional_information_license"/>

### License

```
© 2017 SAP SE https://www.sap.com/

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
