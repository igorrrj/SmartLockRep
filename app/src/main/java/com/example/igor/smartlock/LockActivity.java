package com.example.igor.smartlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Igor on 06.05.2017.
 */

public class LockActivity extends AppCompatActivity {

    private LockView lockView;
    private static String MY_PREFS_NAME = "PatternLock";
    private static String PATTERN_KEY="key";
    SharedPreferences prefs;
    private Button ButtonSave,ButtonAgain;
    SharedPreferences sharedPreferences;
    String saved_password="14789";
    TextView header;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }
        header=(TextView)findViewById(R.id.headerTextView);

        lockView=(LockView)findViewById(R.id.lock_view);



        lockView.setCallBack(new LockView.CallBack() {

            @Override
            public void onFinish(final String password) {

              /*  ButtonAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lockView.Repeate();
                        saved_password="";
                    }
                });
                ButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        saved_password=password;
                        lockView.Repeate();
                        header.setText("Введіть зразок ще раз для збереження");


                    }
                });*/
                if(password.length()>3)
                {
                    if(saved_password.equals(password))
                    {
                    startActivity(new Intent(LockActivity.this,MainActivity.class));

                    }

                    else
                    {
                        lockView.Repeate();
                        //saved_password="";
                        Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();


                    }


                }

            }
        });
    }

}
