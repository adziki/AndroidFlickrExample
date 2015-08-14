package com.meyouhealth.learning.flickrinfo.util;

import android.content.SharedPreferences;

import com.meyouhealth.learning.flickrinfo.app.FlickrApplication;

public class AuthenticationUtil {

    private static final String PREF_AUTH_TOKEN = "flickr-oauth-token";

    public static synchronized String getAuthToken() {
        return FlickrApplication.getPreferences().getString(PREF_AUTH_TOKEN, "");
    }

    public static synchronized void setAuthToken(String token) {
        SharedPreferences.Editor editor = FlickrApplication.getPreferences().edit();
        editor.putString(PREF_AUTH_TOKEN, token);
        editor.apply();
    }

    public static synchronized void clearAuthToken() {
        SharedPreferences.Editor editor = FlickrApplication.getPreferences().edit();
        editor.remove(PREF_AUTH_TOKEN);
        editor.apply();
    }
}
