package com.roomkeeper.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.roomkeeper.R;

public class SettingsFragment extends PreferenceFragment {

    public static final String PHONE_NO = "phone_no";
    public static final String SPARK_ID = "spark_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final EditTextPreference phoneNOPreference = (EditTextPreference) findPreference("phone_no");
        phoneNOPreference.setSummary(prefs.getString(PHONE_NO, ""));
        phoneNOPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String phoneNO = (String) newValue;
                prefs.edit().putString(PHONE_NO, phoneNO).apply();
                phoneNOPreference.setSummary(phoneNO);
                return true;
            }
        });

        final EditTextPreference sparkIDPreference = (EditTextPreference) findPreference("spark_id");
        sparkIDPreference.setSummary(prefs.getString(SPARK_ID, ""));
        sparkIDPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String sparkID = (String) newValue;
                prefs.edit().putString(SPARK_ID, sparkID).apply();
                sparkIDPreference.setSummary(sparkID);
                return true;
            }
        });
    }
}
