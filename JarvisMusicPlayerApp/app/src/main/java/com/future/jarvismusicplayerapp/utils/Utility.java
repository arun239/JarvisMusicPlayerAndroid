package com.future.jarvismusicplayerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.future.jarvismusicplayerapp.R;

/**
 * Created by arungu on 2/3/2016.
 */
public class Utility {
    public static void setUserEmailId(Context context, String emailId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_email_id), emailId);
        editor.apply();
    }

    public static String getUserEmailId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(context.getString(R.string.user_email_id), null);
    }


    public static void setUserName(Context context, String emailId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_name), emailId);
        editor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(context.getString(R.string.user_name), null);
    }


}
