package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *
 */
public class AddNewGoalActivity extends AppCompatActivity {
    private ImageView goalDetailsImageView;

    private static final int REQUEST_CODE = 13;

    private Sensor accelerometer;
    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_goal);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            /**
             * When a 3D motion that the sensors constitute as a shake has been detected,
             * the SecretActivity is loaded
             */
            @Override
            public void onShake() {
                startActivity(new Intent(AddNewGoalActivity.this, SecretActivity.class));
            }
        });
    }

    /**
     * When the user re-enters the app, the sensors start back up and begin
     * monitoring device movements/g-forces in a 3D (x-y-z) span
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * When the user switches apps or clicks on the home button without closing the app,
     * all the sensors that monitor device movments and g-forces are then paused
     * to preserve battery life and RAM
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }

    /**
     * Checks to see if whether or not the appropriate image selections from gallery
     * have been made. If the custom request code matches the actual result code, then
     * an appropriate action was made
     * @param requestCode My custom code (13)
     * @param resultCode The actual value of the code
     * @param data The gallery intent
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            final Uri IMAGE_URI = data.getData();
            //petImageView.setImageURI(IMAGE_URI);
        }
    }

    /**
     * Allows the user to select and change the image for a <code>Goal</code>
     * @param view The ImageView for the <code>Goal</code>
     */
    public final void selectGoalImage(final View view) {
        ArrayList<String> permList =  new ArrayList<>();

        final int CAMERA_PERMISSIONS = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (CAMERA_PERMISSIONS != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        final int WRITING_PERMISSIONS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (WRITING_PERMISSIONS != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final int READING_PERMISSIONS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (READING_PERMISSIONS != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permList.size() > 0) {
            String[] perms = new String[permList.size()];
            ActivityCompat.requestPermissions(this, permList.toArray(perms), REQUEST_CODE);
        }

        if (CAMERA_PERMISSIONS == PackageManager.PERMISSION_GRANTED
                && WRITING_PERMISSIONS == PackageManager.PERMISSION_GRANTED
                && READING_PERMISSIONS == PackageManager.PERMISSION_GRANTED) {
            final Intent GALLERY_INTENT = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(GALLERY_INTENT, REQUEST_CODE);
        }
        else {
            if (CAMERA_PERMISSIONS != PackageManager.PERMISSION_GRANTED
                    && WRITING_PERMISSIONS != PackageManager.PERMISSION_GRANTED
                    && READING_PERMISSIONS != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Bucket List requires camera and " +
                        "external storage permissions.", Toast.LENGTH_SHORT).show();
            else if (CAMERA_PERMISSIONS != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Bucket List requires camera permissions.",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Bucket List requires external storage permissions.",
                        Toast.LENGTH_SHORT).show();
        }
    }
}