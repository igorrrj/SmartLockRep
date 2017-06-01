package com.example.igor.smartlock.Registration;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.igor.smartlock.R;

/**
 * Created by Igor on 28.05.2017.
 */

public class RegisterActivity extends AppCompatActivity {
    Button loginButton;
    EditText name,password;
    TextView registerText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        final ViewPager viewPager=(ViewPager)findViewById(R.id.container);

        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager,true);

        ImageButton button=(ImageButton) findViewById(R.id.btn_next);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()==1)
                {
                    Intent intent=new Intent(RegisterActivity.this,LockRegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }
        });

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position)
            {
                case 0: return new UserDataRegisterFragment();
                case 1: return new UserPhotoRegisterFragment();
            }

            return null;
        }

        @Override
        public int getCount() { //кількість сторінок що відображаються
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {  //імена табів

            return null;

        }
    }
}
