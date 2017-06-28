# Ariba Public Sourcing

## Content:

- [Overview](#overview)
- [Technical Details](#technical_details)
- [Prerequisites](#prerequisites)
- [Create a Discovery RFX Publication to External Marketplace Application](#setup_ariba)
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

Ariba Public Sourcing is a sample extension application for [Ariba Network](https://www.ariba.com/) that runs on [SAP Cloud Platform](https://cloudplatform.sap.com/). The purpose of the application is to collect public sourcing events from Ariba Discovery via [Ariba Open APIs](https://developer.ariba.com/api) and to display them in an application running on SAP Cloud Platform.

The application uses the Discovery RFX Publication to External Marketplace API. It can be either run on the productive SAP Cloud Platform landscape, or the trial landscape. This guide explains how to download, build, deploy and configure the application on the SAP Cloud Platform trial landscape.

These are the SAP Cloud Platform services and features in use:
* [Connectivity Service](https://help.hana.ondemand.com/help/frameset.htm?e54cc8fbbb571014beb5caaf6aa31280.html) - the application uses the Connectivity Service to obtain connection to Ariba Open APIs.
* [Persistence Service](https://help.hana.ondemand.com/help/frameset.htm?e7b3c275bb571014a910b3fb4329cf09.html) - the application uses the Persistence Service.

<a name="technical_details"/>

## Technical Details

The Ariba Public Sourcing extension is a Java application that calls Ariba's Discovery RFX Publication to External Marketplace API and fetches all available public sourcing events. The events are persisted in a database and displayed in a SAPUI5 front-end.
The events could be either fetched manually through the UI or automatically with a scheduler.

To use this extension application, you need to:

1. Create an Ariba Open APIs application in [Ariba Developer Portal](https://developer.ariba.com/api).
2. Build and deploy the Java extension application on SAP Cloud Platform.
3. Configure the Java application to use the Ariba Open APIs application.
4. Start the Java extension application.

<a name="prerequisites"/>

## Prerequisites

You need to:
* have an account for [Ariba Developer Portal](https://developer.ariba.com/api)
* have an [SAP Cloud Platform developer account](https://help.hana.ondemand.com/help/frameset.htm?e4986153bb571014a2ddc2fdd682ee90.html)
* download or clone the project with Git
* have set up [Maven 3.0.x](http://maven.apache.org/docs/3.0.5/release-notes.html)

<a name="setup_ariba"/>

## Create a Discovery RFX Publication to External Marketplace Application in Ariba Developer Portal

You already have an account for Ariba Developer Portal. Open the [guide](https://developer.ariba.com/api/guides) and follow the steps to create a new SAP Ariba Open APIs application that will be used against Ariba Open APIs sandbox environment.
At the end, you should have an application key related to the SAP Ariba Open APIs application. You will need it in order to call the Discovery RFX Publication to External Marketplace API, the sandbox environment.

>Note: when working against Open API sandbox environment, you will have only an application key. When you enable your SAP Ariba Open APIs application for production usage, you will get an OAuth access token as well. In that case, just add the access token to the Java application destination.

>Note: to enable the Ariba Open APIs application for production usage, follow the guide on the [Ariba Open APIs Developer Portal](https://developer.ariba.com/api/guides).

<a name="build_deploy"/>

## Build and Deploy the Application on SAP Cloud Platform

You have already downloaded or cloned the Public Sourcing extension application. Now you have to build the application and deploy it on the SAP Cloud Platform. There are two paths you can choose from: 

* using the SAP Cloud Platform Cockpit
* using the Eclipse IDE

<a name="build_deploy_cockpit"/>

### Using the SAP Cloud Platform Cockpit

#### Build the Application

1. Go to the `cloud-ariba-public-sourcing-ext` folder.
2. Build the project with:

        mvn clean install

The produced WAR file `ROOT.war` under target sub-folder `cloud-ariba-discovery-rfx-to-external-marketplace-ext\target` is ready to be deployed.

#### Deploy the Application Using the Cockpit

You have to [deploy](https://help.hana.ondemand.com/help/frameset.htm?abded969628240259d486c4b29b3948c.html) the `ROOT.war` file as a Java application via SAP Cloud Platform Cockpit. Use Java Web Tomcat 8 as a runtime option.

<a name="build_deploy_eclipse"/>

### Using the Eclipse IDE

When using the Eclipse IDE you can take a look at the structure and code of the application. You have to import the application as an existing Maven project and build it with Maven using `clean install`. You also have to choose Java Web Tomcat 8 as a runtime option.

#### Prerequisites

* [JDK 8 or later](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Eclipse IDE for Java EE Developers](https://eclipse.org/downloads/)
* [SAP Cloud Platform Tools](https://tools.hana.ondemand.com/#cloud) ([Installation help](https://help.hana.ondemand.com/help/frameset.htm?e815ca4cbb5710148376c549fd74c0db.html))

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
3. Select _SAP Cloud Platform_ and choose _Next_.
4. Select _Java Web Tomcat 8_ as a runtime option. 
5. Add the application to the _Configured_ field and choose _Finish_.

<a name="configure"/>

## Create a Destination


You need to [create an HTTP destination on the SAP Cloud Platform](https://help.hana.ondemand.com/help/frameset.htm?1e110da0ddd8453aaf5aed2485d84f25.html).


   Use the following required properties:

                Type: HTTP
                Name: ariba-public-sourcing
                URL: <SAP Ariba OpenAPIs Environment Url>
                Authentication: NoAuthentication
                
   And add four additional properties:

                JobIntervalInSeconds: <How often will the Discovery RFX Publication to External Marketplace API be called>
                SiteId: <The unique site id. Enter random string in case of sandbox environment>
                ApiKey: <SAP Ariba Open APIs application API key>
		AccessToken: <SAP Ariba Open APIs application API access token. Enter random string in case of sandbox environment>
		
>Note: There is a sample destination in the project's resources folder.

<a name="start"/>

## Start the Application

After creating the destination, [start (or restart in case the application is already started) the application via the Cloud Cockpit](https://help.hana.ondemand.com/help/frameset.htm?7612f03c711e1014839a8273b0e91070.html). 

<a name="notes"/>

## Notes On Retrieving Sourcing Events

The automatic retrieving of sourcing events from Ariba is commented out in the source code of the Java application and the retrieving is done manually through the UI. If you prefer, you could turn on the automatic retrieving by uncommenting the related logic in PublicSourcingContextListener.java.

<a name="additional_information"/>

## Additional Information

<a name="additional_information_resources"/>

### Resources
* SAP Cloud Documentation - https://help.hana.ondemand.com/
* SAP Ariba OpenAPI - https://developer.ariba.com/api

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
