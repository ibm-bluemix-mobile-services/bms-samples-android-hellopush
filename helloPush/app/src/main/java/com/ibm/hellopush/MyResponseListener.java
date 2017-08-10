package com.ibm.hellopush;

import android.util.Log;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;

import org.json.JSONObject;

/**
 * Created by Apple on 18/07/17.
 */

public class MyResponseListener implements ResponseListener {

    @Override
    public void onSuccess(Response response) {
        if (response != null) {
            Log.i("MyApp", "Response status: " + response.getStatus());
            Log.i("MyApp", "Response headers: " + response.getHeaders());
            Log.i("MyApp", "Response body: " + response.getResponseText());
        }
    }

    @Override
    public void onFailure(Response response, Throwable t, JSONObject extendedInfo) {
        if (response != null) {
            Log.i("MyApp", "Response status: " + response.getStatus());
            Log.i("MyApp", "Response body: " + response.getResponseText());
        }
        if (t != null && t.getMessage() != null) {
            Log.i("MyApp", "Error: " + t.getMessage());
        }
    }
}