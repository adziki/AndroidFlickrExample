package com.meyouhealth.learning.flickrinfo.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.meyouhealth.learning.flickrinfo.com.RestClient;

public class FlickrApplication extends Application {

    private static Context context;
    private final static String PREFS_NAME = "FlickrPreferences";

    public static SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FlickrApplication.context = this;
    }

    public static RestClient getRestClient() {
        return (RestClient) RestClient.getInstance(RestClient.class, FlickrApplication.context);
    }
}
