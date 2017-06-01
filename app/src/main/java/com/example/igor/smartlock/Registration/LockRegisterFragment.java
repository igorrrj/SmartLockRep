package com.example.igor.smartlock.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igor.smartlock.LockActivity;
import com.example.igor.smartlock.LockView;
import com.example.igor.smartlock.R;

import static com.example.igor.smartlock.LockActivity.PREFS_USER_DATA;
import static com.example.igor.smartlock.LockActivity.USER_LOCK_KEY;

/**
 * Created by Igor on 29.05.2017.
 */

public class LockRegisterFragment extends Fragment {
    private LockView lockView;
    String saved_password;
    TextView header;
    TextView againText,saveText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lock_register,container, false);
        lockView=(LockView)rootView.findViewById(R.id.lock_view);

        againText=(TextView)rootView.findViewById(R.id.textAgain);
        saveText=(TextView)rootView.findViewById(R.id.textSave);

        header=(TextView)rootView.findViewById(R.id.headerTextView);

        lockView.setCallBack(new LockView.CallBack() {

            @Override
            public void onFinish(final String password) {

                SharedPreferences sp=getActivity().getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE);
                sp.edit().putString(USER_LOCK_KEY,password).apply();
                Toast.makeText(getActivity(),"password"+saved_password,Toast.LENGTH_SHORT).show();
                Log.e("PASStYped",password);

                againText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lockView.Repeate();
                        saved_password="";
                    }
                });

                saveText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saved_password=password;
                        header.setText("Введіть зразок ще раз для збереження");

                    }
                });

                if(saved_password!="")
                {
                    if(saved_password.equals(password))
                    {
                        startActivity(new Intent(getActivity(),LockActivity.class));
                        SharedPreferences sip=getActivity().getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE);
                        sip.edit().putString(USER_LOCK_KEY,saved_password).apply();
                        Toast.makeText(getActivity(),"password"+saved_password,Toast.LENGTH_SHORT).show();
                        Log.e("PASSREG",saved_password);
                    }

                    else
                    {
                        lockView.Repeate();
                        saved_password="";
                        Toast.makeText(getActivity(),"password are different", Toast.LENGTH_SHORT).show();
                    }
                }

                }
        });


        return rootView;
    }
}
