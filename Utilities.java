package com.haptix.authtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by gordonwallace on 13/09/2017.
 */

public class Utilities {

    public static void saveToken(String token, Context ctx){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN", token);
        editor.commit();
    }

    public static String getToken(Context ctx){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String token = sharedPref.getString("TOKEN", "");

        return token;
    }
}
