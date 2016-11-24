package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private Timer splashTimer;
    private TimerTask splashTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashTimerTask = new TimerTask() {
            /**
             *
             */
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreenActivity.this, GoalListActivity.class));
            }
        };

        splashTimer = new Timer();
        splashTimer.schedule(splashTimerTask, 3000);
    }
}
