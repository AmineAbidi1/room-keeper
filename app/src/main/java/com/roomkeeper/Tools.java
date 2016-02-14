package com.roomkeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.roomkeeper.settings.SettingsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {

    static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());


    public static String formatTime(long time) {
        return sdf.format(new Date(time));
    }


    public static String getCurrentUser(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(SettingsFragment.NICKNAME, "");
    }
}
