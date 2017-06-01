package com.example.igor.smartlock.FragmentsMenu;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.igor.smartlock.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Igor on 08.05.2017.
 */

public class ProgressLockFragment extends Fragment {
    ProgressBar lockBar;
    Button buttonProgress;
    ObjectAnimator animation;
    Drawable lockProgressD;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_progress_lock,container, false);

        animation = ObjectAnimator.ofInt (lockBar, "progress", 0,100);
        animation.setDuration (1000); //in milliseconds
        animation.setInterpolator(new LinearInterpolator());

        lockBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
        lockProgressD=lockBar.getProgressDrawable();

        buttonProgress=(Button)rootView.findViewById(R.id.buttonProgress);
        final Drawable lockProgressD = getContext().getResources().getDrawable(R.drawable.circular);


        buttonProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                animation.start();
                buttonProgress.setClickable(false);

            }
        });


        return rootView;
    }
    Timer timer;
    Handler handler;
    int progress=0;
    Boolean ans = true;
            void startTimer()
            {
                timer=new Timer();
                handler=new Handler();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(ans)
                                    progress++;
                                else progress--;

                                lockBar.setRotation(progress);

                               // Log.e("Rotation",lockBar.getRotation()+"");

                                if(progress==180 && ans)
                                {
                                    timer.cancel();
                                    ans=!ans;
                                    buttonProgress.setText("Closed");
                                    lockProgressD.setColorFilter(new
                                            PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
                                    buttonProgress.setClickable(true);

                                }
                                if(progress==0 && !ans)
                                {
                                    timer.cancel();
                                    ans=!ans;
                                    buttonProgress.setText("Opened");
                                    buttonProgress.setClickable(true);
                                    lockProgressD.setColorFilter(new
                                            PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));

                                }



                            }
                        });
            }
        },0,5);
    }

}
