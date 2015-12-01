# Android HelloPush application for IBM MobileFirst Services on IBM Bluemix
---
### Create an Android HelloPush sample application.
1. Download the HelloPush sample.
2. Configuring the backend for your HelloPush application.
3. Configuring the front end in the HelloPush example.
4. Run the Android app.

### Before your begin 
Before you start, make sure you have the following:
- A [Bluemix](http://bluemix.net) account.
- Google Cloud Messaging (GCM) credentials.  To obtain your GCM credentials, follow the instructions at  
https://www.ng.bluemix.net/docs/services/mobilepush/t_push_config_provider_android.html

**Note**: You'll need the project number (Sender ID) and API key to configure your credentials on the Bluemix Push Notification Dashboard.

### Download the HelloPush sample
Clone the sample from GitHub using the following command:

git clone https://github.com/ibm-bluemix-mobile-services/bms-samples-android-hellopush

### Configuring the back end for your HelloPush application
Before you can run the HelloPush application, you must set up an app on Bluemix.  The following procedure shows you how to create a MobileFirst Services Starter application. The Starter application creates a Node.js runtime environment so that you can provide server-side functions, such as resource URIs and static files.  The Cloudant® NoSQL DB, Push Notifications, and Mobile Client Access services are added to the app.

1. Create a mobile backend in the Bluemix dashboard.
2. In the **Boilerplates** section Bluemix catalog, click **MobileFirst Services Starter**.
3. Enter a name and host for your mobile backend and click Create.
4. Click **Finish**.

### Configuring the front end in the HelloPush sample
1. In Android Studio, open the helloPush Android project.
2. Run a Gradle sync (usually starts automatically) to import the required `core` and `push` SDKs. You can view the **build.gradle** file in the following directory:

	`helloPush\app\build.gradle`
	
3. Open the `MainActivity.java` class.
4. In the application `onCreate` method, add the corresponding `ApplicationRoute` and `ApplicationID`:


Java:

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		...
		
        try {
            //initialize SDK with IBM Bluemix application ID and route
            //TODO: Please replace <APPLICATION_ROUTE> with a valid ApplicationRoute and <APPLICATION_ID> with a valid ApplicationId
            BMSClient.getInstance().initialize(this, "<APPLICATION_ROUTE>", "<APPLICATION_ID>");
        }
        catch (MalformedURLException mue) {
            ....
        }


### Run the Android app
You can register and receive push notifications on an Android simulator or physical device.

When you run the application, it displays the **Register for Push** button.

When you click this button, the application attempts to register the device and application to the Push Notification Service. The app displays an alert showing the registration status (successful or failed).

When a push notification is received and the application is in the foreground, an alert is displayed showing the notification's content.

The application uses the `ApplicationRoute` and `ApplicationID` specified in the `onCreate` method to connect to the IBM Push Notification Service on Bluemix. The registration status and content is displayed in the logcat Console.
### License
This package contains sample code provided in source code form. The samples are licensed under the under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 and may also view the license in the license.txt file within this package. Also see the notices.txt file within this package for additional notices.

