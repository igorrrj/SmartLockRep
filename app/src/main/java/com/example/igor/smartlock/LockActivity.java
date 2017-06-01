package com.example.igor.smartlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Igor on 06.05.2017.
 */

public class LockActivity extends AppCompatActivity {

    private LockView lockView;
    SharedPreferences prefs;
    SharedPreferences sharedPreferences;
    String saved_password;
    TextView header;

    public final static String PREFS_USER_DATA="data";
    public final static String USER_LOCK_KEY="key_lock";
    public final static String USER_NAME_KEY="key_name";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.e("LOckACtivity","YEs");
        SharedPreferences sp=getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE);
        Log.e("NAME", sp.getString(USER_NAME_KEY,"") + " | Password " + sp.getString(USER_LOCK_KEY,"")+"");
        if (!sp.contains(USER_NAME_KEY) && !sp.contains(USER_LOCK_KEY) ) {
            startActivity(new Intent(LockActivity.this, LoginActivity.class));
            finish();
        }


        header=(TextView)findViewById(R.id.headerTextView);

        lockView=(LockView)findViewById(R.id.lock_view);

        saved_password=sp.getString(USER_LOCK_KEY,"");


        lockView.setCallBack(new LockView.CallBack() {

            @Override
            public void onFinish(final String password) {

                if(password.length()>3)
                {
                    if(saved_password.equals(password))
                    {
                    startActivity(new Intent(LockActivity.this,MainActivity.class));
                    finish();
                    }

                    else
                    {
                        lockView.Repeate();
                        //saved_password="";
                        Log.e("PSSS",saved_password+"f");
                        Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }


}
