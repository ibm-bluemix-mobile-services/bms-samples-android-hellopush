# Android helloPush Sample Application for Bluemix Mobile Services
---
This Android helloPush sample contains an Android project that you can use to learn more about the IBM Push Notification Service.

Use the following steps to configure the helloPush sample for Android:

1. [Download the helloPush sample](#download-the-hellopush-sample).
2. [Configure the mobile backend for your helloPush application](#configure-the-mobile-back-end-for-your-hellopush-application).
3. [Configure the front end in the helloPush example](#configure-the-front-end-in-the-hellopush-sample).
4. [Run the Android app](#run-the-android-app).


### Before you begin

Before you start, make sure you have the following:
- A [Bluemix](http://bluemix.net) account.
- Google Cloud Messaging (GCM) credentials.  To obtain your GCM credentials, follow the instructions at  
https://www.bluemix.net/docs/services/mobilepush/t_push_provider_android.html

>**Note**: You'll need the project number (Sender ID) and API key to configure your credentials on the Bluemix Push Notification Dashboard.

### Download the helloPush sample

Clone the sample from GitHub using the following command:

`git clone https://github.com/ibm-bluemix-mobile-services/bms-samples-android-hellopush`


### Configure the mobile back end for your helloPush application

Before you can run the helloPush application, you must set up an app on Bluemix.  The following procedure shows you how to create a MobileFirst Services Starter application. The Starter application creates a Node.js runtime environment so that you can provide server-side functions, such as resource URIs and static files.  The Cloudant® NoSQL DB, Push Notifications, and Mobile Client Access services are added to the app.

##### Create a mobile backend in the Bluemix dashboard:

1. In the **Boilerplates** section Bluemix catalog, click **MobileFirst Services Starter**.
2. Enter a name and host for your mobile backend and click **Create**.
3. Once complete, navigate to your Push service dashboard and select  **Configure**.
4. From the **Configure** page, you will see the **Mobile Options** button in the upper right hand corner. Click the button and save the *App Guid* and *Client Secret* values, they will be needed later.

##### Configure Push Notification Service:

1. In the **Configuration** tab you will now need to configure your Push Notification Service.
2. Navigate to the **GCM Push Credentials** section. Enter your GCM project credentials, project number (Sender ID) and API key, and click **save**.


### Configure the front end in the helloPush sample

1. In Android Studio, open the helloPush Android project.
2. Download Firebase `google-services.json` for android, and place them in the root folder of the project. Follow this (Doc)[https://console.bluemix.net/docs/services/mobilepush/push_step_1.html#push_step_1]
3. Run a Gradle sync (usually starts automatically) to import the required `Core` and `Push` SDKs. You can view the **build.gradle** file in the following directory:

	`helloPush\app\build.gradle`

4. Open the `MainActivity.java` class.
5. In the application `onCreate` method, add the corresponding `App Guid` and `Client Secret` that you saved earlier to your push.init function:

```Java
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize core SDK with IBM Bluemix application Region, TODO: Update region if not using Bluemix US SOUTH
        BMSClient.getInstance().initialize(this, BMSClient.REGION_US_SOUTH);

        // Grabs push client sdk instance
        push = MFPPush.getInstance();
        push.initialize(this, "<APP_GUID>", "<CLIENT_SECRET>");
```

> **Note**: If your Bluemix app is **not** hosted in US_SOUTH, be sure to update the region parameter appropriately: BMSClient.REGION_SYDNEY or BMSClient.REGION_UK.




### Run the Android app

You can register and receive push notifications on an Android simulator or physical device.

When you run the application, it displays the **Register for Push** button. When you click this button, the application attempts to register the device and application to the Push Notification Service. The app displays an alert showing the registration status (successful or failed).

To send a push notification from the Bluemix dashboard follow [Sending basic push notifications](https://www.bluemix.net/docs/services/mobilepush/t_send_push_notifications.html)

When a push notification is received and the application is in the foreground, an alert is displayed showing the notification's content. The application uses the `App Guid` and `Client Secret` specified in the `onCreate` method to connect to the IBM Push Notification Service on Bluemix and associate a unigue User Id. The registration status and content is displayed in the logcat console and on screen.


### Samples & videos

* Please visit for samples - [Github Sample](https://github.com/ibm-bluemix-mobile-services/bms-samples-android-hellopush)

* Video Tutorials Available here - [Bluemix Push Notifications](https://www.youtube.com/channel/UCRr2Wou-z91fD6QOYtZiHGA)


### License

This package contains sample code provided in source code form. The samples are licensed under the under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 and may also view the license in the license.txt file within this package. Also see the notices.txt file within this package for additional notices.
