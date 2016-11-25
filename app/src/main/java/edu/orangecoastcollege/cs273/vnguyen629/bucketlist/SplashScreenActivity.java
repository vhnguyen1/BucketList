package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Loads a splash screen the moment the user opens up the app before automatically
 * moving onto the next page
 *
 * @author Vincent Nguyen
 */
public class SplashScreenActivity extends AppCompatActivity {
    private Timer splashTimer;
    private TimerTask splashTimerTask;

    /**
     * Loads up the timer for 3 total seconds before automatically moving
     * to the GoalsListActivity page
     * @param savedInstanceState The current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashTimerTask = new TimerTask() {
            /**
             * Starts the activity for the GoalsListActivity page after finishing
             * the 3 second countdown
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