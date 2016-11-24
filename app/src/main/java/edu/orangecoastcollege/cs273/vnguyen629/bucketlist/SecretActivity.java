package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 *
 */
public class SecretActivity extends AppCompatActivity {
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        Toast.makeText(this, "Welcome to Vincent's Secret Page!", Toast.LENGTH_SHORT).show();
    }
}