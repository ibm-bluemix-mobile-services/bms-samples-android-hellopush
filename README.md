# HelloPush application for IBM MobileFirst Services on IBM Bluemix
---
The HelloPush sample contains an Android project that you can use to learn the IBM Push Notification Service.  
### Downloading the samples
Clone the samples from GitHub with the following command:

git clone https://github.com/ibm-bluemix-mobile-services/bms-samples-android-hellopush

### Configure the back end for your Bluelist application
Before you can run the Bluelist application, you must set up an app on Bluemix.  For simplicity, the steps below outline how to create a MobileFirst Services Starter application which includes the following: Node.js runtime, IBM Push Notifications, Mobile Client Access, and Cloudant services.

1. Sign up for a [Bluemix](http://bluemix.net) Account.
2. Create a mobile app.  In the Boilerplates section Bluemix catalog, click **MobileFirst Services Starter**.  Choose a **Name** and click **Create**.
3. Configure Push: 
	- If you don't have one already, create a GCM project in the [Google developer console](https://console.developers.google.com)
	- Navigate to **Credentials** underneath **APIs & Auth**
	- Select **Add credentials** --> **Api Key** --> **Server Key** --> **Create**
	- In the IBM Push Notifications Dashboard, enter the API Key you just created along with the GCM Project number underneath Google Cloud Messaging.

### Configure the front end in the HelloPush sample
1. Open helloPush Android project in Android Studio
2. Run a Gradle sync (usually kicked off automatically) to pull in the required `core` and `push` SDKs. Check out the build.gradle file (`helloPush\app\build.gradle`) for more info.
3. Open the MainActivity.java class and add the corresponding ApplicationRoute and ApplicationID in the application's `onCreate` method:


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


### Run the Android App
You can register and receive push notifications on an Android simulator or physical device.

When you run the application you will see a single view application with a "Register for Push" button. When you click this button the application will attempt to register the device and application for push notifications. An alert will be displayed in the app showing if the registration was successful or not. When a push notification is received and the application is in the foreground, an alert will be displayed showing the notification's content.'The application uses the ApplicationRoute and ApplicationID specified in the MainActivity in order to connect against the IBM Push Notification Service on Bluemix. Registration status and other content is also output in the logcat Console 

### License
This package contains sample code provided in source code form. The samples are licensed under the under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 and may also view the license in the license.txt file within this package. Also see the notices.txt file within this package for additional notices.

