package com.example.igor.smartlock.Registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.igor.smartlock.R;

import static com.example.igor.smartlock.LockActivity.PREFS_USER_DATA;
import static com.example.igor.smartlock.LockActivity.USER_NAME_KEY;

/**
 * Created by Igor on 29.05.2017.
 */

public class UserDataRegisterFragment extends Fragment {
     EditText user_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_data_register,container, false);

        user_name=(EditText)rootView.findViewById(R.id.input_user_name);
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences sp=getActivity().getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE);
                sp.edit().putString(USER_NAME_KEY,user_name.getText().toString().trim()).apply();
                Log.e("USER_NAME",user_name.getText().toString().trim());
            }
        });

        return rootView;
    }
}