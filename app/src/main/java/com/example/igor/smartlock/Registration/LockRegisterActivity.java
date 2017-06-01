package com.example.igor.smartlock.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class LockRegisterActivity extends AppCompatActivity {

    private LockView lockView;
    String saved_password="";
    TextView header;
    TextView againText,saveText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lock_register);

        header=(TextView)findViewById(R.id.headerTextView);

        lockView=(LockView)findViewById(R.id.lock_view);

        againText=(TextView)findViewById(R.id.textAgain);
        saveText=(TextView)findViewById(R.id.textSave);


        lockView.setCallBack(new LockView.CallBack() {

            @Override
            public void onFinish(final String password) {

                againText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lockView.Repeate();
                        saved_password="";
                    }
                });
                saveText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        saved_password=password;
                        lockView.Repeate();
                        header.setText("Введіть зразок ще раз для збереження");

                    }
                });
                if(saved_password!="")
                {
                    if (saved_password.equals(password)) {
                        Toast.makeText(getApplicationContext(), "Password Set", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LockRegisterActivity.this,LockActivity.class));
                        SharedPreferences sip=getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE);
                        sip.edit().putString(USER_LOCK_KEY,saved_password).apply();
                        finish();


                    } else {
                        Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
                        lockView.Repeate();
                        saved_password = "";

                    }
                }


            }
        });
    }

}
