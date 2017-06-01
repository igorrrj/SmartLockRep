package com.example.igor.smartlock.Preference;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import com.example.igor.smartlock.R;
import com.example.igor.smartlock.Registration.LockRegisterActivity;

/**
 * Created by Igor on 27.05.2017.
 */

public class SettingsFragment extends PreferenceFragment {
    Preference passwordPref;
    SwitchPreference switchPreference;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        sharedPreferences=getActivity().getSharedPreferences("bluetooth_pref", Context.MODE_PRIVATE);
        passwordPref=(Preference)findPreference("password_pref");
        passwordPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), LockRegisterActivity.class));
                return false;
            }
        });

        switchPreference=(SwitchPreference)findPreference("auto_bluetooth_pref");
        switchPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                if(switchPreference.isEnabled())
                {
                    sharedPreferences.edit().putBoolean("bluetooth",true);
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.enable();
                    }
                }
                else
                {
                    sharedPreferences.edit().putBoolean("bluetooth",false);
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.disable();
                    }
                }
                sharedPreferences.edit().apply();
                return false;
            }
        });

    }
}
