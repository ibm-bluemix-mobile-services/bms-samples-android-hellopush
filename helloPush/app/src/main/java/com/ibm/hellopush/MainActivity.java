package com.ibm.hellopush;
/**
 * Copyright 2015, 2016 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationButton;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationCategory;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationOptions;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.ibm.mobilefirstplatform.clientsdk.android.security.api.AuthorizationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MFPPush push; // Push client
    private MFPPushNotificationListener notificationListener; // Notification listener to handle a push sent to the phone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize core SDK with IBM Bluemix application Region, TODO: Update region if not using Bluemix US SOUTH
        BMSClient.getInstance().initialize(this, BMSClient.REGION_US_SOUTH);

        // Grabs push client sdk instance
        push = MFPPush.getInstance();
        // Initialize Push client
        // You can find your App Guid and Client Secret by navigating to the Configure section of your Push dashboard, click Mobile Options (Upper Right Hand Corner)
        // TODO: Please replace <APP_GUID> and <CLIENT_SECRET> with a valid App GUID and Client Secret from the Push dashboard Mobile Options
        push.initialize(this, "<APP_GUID>", "<CLIENT_SECRET>");


        //TODO: Actionable Notifications

       /* MFPPushNotificationOptions options = new MFPPushNotificationOptions();

        MFPPushNotificationButton firstButton = new MFPPushNotificationButton.Builder("Accept Button")
                .setIcon("check_circle_icon")
                .setLabel("Accept")
                .build();

        MFPPushNotificationButton secondButton = new MFPPushNotificationButton.Builder("Decline Button")
                .setIcon("extension_circle_icon")
                .setLabel("Decline")
                .build();

        MFPPushNotificationButton secondButton1 = new MFPPushNotificationButton.Builder("Decline Button2")
                .setIcon("extension_circle_icon")
                .setLabel("Decline2")
                .build();

        List<MFPPushNotificationButton> getButtons =  new ArrayList<MFPPushNotificationButton>();
        getButtons.add(firstButton);
        getButtons.add(secondButton);
        getButtons.add(secondButton1);

        List<MFPPushNotificationButton> getButtons1 =  new ArrayList<MFPPushNotificationButton>();
        getButtons1.add(firstButton);
        getButtons1.add(secondButton);

        List<MFPPushNotificationButton> getButtons2 =  new ArrayList<MFPPushNotificationButton>();
        getButtons2.add(firstButton);

        MFPPushNotificationCategory category = new MFPPushNotificationCategory.Builder("First_Button_Group1").setButtons(getButtons).build();
        MFPPushNotificationCategory category1 = new MFPPushNotificationCategory.Builder("First_Button_Group2").setButtons(getButtons1).build();
        MFPPushNotificationCategory category2 = new MFPPushNotificationCategory.Builder("First_Button_Group3").setButtons(getButtons2).build();

        List<MFPPushNotificationCategory> categoryList =  new ArrayList<MFPPushNotificationCategory>();
        categoryList.add(category);
        categoryList.add(category1);
        categoryList.add(category2);

        options.setInteractiveNotificationCategories(categoryList);
        */


        //TODO: Set custom DeviceId
        //options.setDeviceid("your_device_id");


        // Create notification listener and enable pop up notification when a message is received
        notificationListener = new MFPPushNotificationListener() {
            @Override
            public void onReceive(final MFPSimplePushNotification message) {
                Log.i(TAG, "Received a Push Notification: " + message.toString());
                runOnUiThread(new Runnable() {
                    public void run() {
                        new android.app.AlertDialog.Builder(MainActivity.this)
                                .setTitle("Received a Push Notification")
                                .setMessage(message.getAlert())
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                })
                                .show();
                    }
                });
            }
        };

    }


    /**
     * Called when the register device button is pressed.
     * Attempts to register the device with your push service on Bluemix.
     * If successful, the push client sdk begins listening to the notification listener.
     * Also includes the example option of UserID association with the registration for very targeted Push notifications.
     *
     * @param view the button pressed
     */
    public void registerDevice(View view) {

        // Checks for null in case registration has failed previously
        if(push==null){
            push = MFPPush.getInstance();
        }

        // Make register button unclickable during registration and show registering text
        TextView buttonText = (TextView) findViewById(R.id.button_text);
        buttonText.setClickable(false);
        TextView responseText = (TextView) findViewById(R.id.response_text);
        responseText.setText(R.string.Registering);
        Log.i(TAG, "Registering for notifications");

        // Creates response listener to handle the response when a device is registered.
        MFPPushResponseListener registrationResponselistener = new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                // Split response and convert to JSON object to display User ID confirmation from the backend
                String[] resp = response.split("Text: ");
                try {
                    JSONObject responseJSON = new JSONObject(resp[1]);
                    setStatus("Device Registered Successfully with USER ID " + responseJSON.getString("userId"), true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "Successfully registered for push notifications, " + response);
                // Start listening to notification listener now that registration has succeeded
                push.listen(notificationListener);
            }

            @Override
            public void onFailure(MFPPushException exception) {
                String errLog = "Error registering for push notifications: ";
                String errMessage = exception.getErrorMessage();
                int statusCode = exception.getStatusCode();

                // Set error log based on response code and error message
                if(statusCode == 401){
                    errLog += "Cannot authenticate successfully with Bluemix Push instance, ensure your CLIENT SECRET was set correctly.";
                } else if(statusCode == 404 && errMessage.contains("Push GCM Configuration")){
                    errLog += "Push GCM Configuration does not exist, ensure you have configured GCM Push credentials on your Bluemix Push dashboard correctly.";
                } else if(statusCode == 404 && errMessage.contains("PushApplication")){
                    errLog += "Cannot find Bluemix Push instance, ensure your APPLICATION ID was set correctly and your phone can successfully connect to the internet.";
                } else if(statusCode >= 500){
                    errLog += "Bluemix and/or your Push instance seem to be having problems, please try again later.";
                }

                setStatus(errLog, false);
                Log.e(TAG,errLog);
                // make push null since registration failed
                push = null;
            }
        };

        // Attempt to register device using response listener created above
        // Include unique sample user Id instead of Sample UserId in order to send targeted push notifications to specific users
        //TODO : Registartion with UserId
        push.registerDeviceWithUserId("Sample UserID",registrationResponselistener);

        //TODO: Registartion without UserId
        //push.registerDevice(registrationResponselistener);
    }


    //TODO: Call this method to get all the Tags

    /**
     * Call for getting the tags.
     */
    public void getTags() {

        push.getTags(new MFPPushResponseListener<List<String>>() {
            @Override
            public void onSuccess(List<String> tags) {
                setStatus("Retrieved Tags : " + tags, true);
            }

            @Override
            public void onFailure(MFPPushException ex) {
                setStatus("Error getting tags..." + ex.getMessage(), false);
            }
        });
    }

    //TODO: Call this method to get all the subscribed tags
    /**
     * Call for getting the subscribed tags.
     */
    public void getSubscriptions() {

        push.getSubscriptions(new MFPPushResponseListener<List<String>>() {
            @Override
            public void onSuccess(List<String> tags) {
                setStatus("Retrieved subscriptions : " + tags, true);
            }

            @Override
            public void onFailure(MFPPushException ex) {
                setStatus("Error getting subscriptions.. "
                        + ex.getMessage(), false);
            }
        });
    }

    //TODO: Call this method for subscribing to a tag
    /**
     * Call for subscribing to a tag.
     */
    public void subscribeToTag(final String tagName) {

        push.subscribe(tagName,new MFPPushResponseListener<String>() {
                    @Override
                    public void onFailure(MFPPushException ex) {
                        setStatus("Error subscribing to " + tagName + ex.getMessage(), false);
                    }

                    @Override
                    public void onSuccess(String arg0) {
                        setStatus("Succesfully Subscribed to: "+ tagName, true);
                    }
                });
    }

    //TODO: Call this method for Unsubscribing to a tag
    /**
     * Call for Unsubscribing to a tag.
     */
    void unsubscribeFromTags(final String tag) {
        push.unsubscribe(tag, new MFPPushResponseListener<String>() {

            @Override
            public void onSuccess(String s) {
                setStatus("Successfully unsubscribed from tag . " + tag, true);
            }

            @Override
            public void onFailure(MFPPushException e) {
                setStatus("Error while unsubscribing from tags. " + e.getMessage(), false);
            }

        });
    }

    //TODO: Call this
    // If the device has been registered previously, hold push notifications when the app is paused
    @Override
    protected void onPause() {
        super.onPause();

        if (push != null) {
            push.hold();
        }
    }

    // If the device has been registered previously, ensure the client sdk is still using the notification listener from onCreate when app is resumed
    @Override
    protected void onResume() {
        super.onResume();
        if (push != null) {
            push.listen(notificationListener);
        }
    }

    /**
     * Manipulates text fields in the UI based on initialization and registration events
     * @param messageText String main text view
     * @param wasSuccessful Boolean dictates top 2 text view texts
     */
    private void setStatus(final String messageText, boolean wasSuccessful){
        final TextView responseText = (TextView) findViewById(R.id.response_text);
        final TextView topText = (TextView) findViewById(R.id.top_text);
        final TextView bottomText = (TextView) findViewById(R.id.bottom_text);
        final TextView buttonText = (TextView) findViewById(R.id.button_text);
        final String topStatus = wasSuccessful ? "Yay!" : "Bummer";
        final String bottomStatus = wasSuccessful ? "You Are Connected" : "Something Went Wrong";

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonText.setClickable(true);
                responseText.setText(messageText);
                topText.setText(topStatus);
                bottomText.setText(bottomStatus);
            }
        });
    }
}
