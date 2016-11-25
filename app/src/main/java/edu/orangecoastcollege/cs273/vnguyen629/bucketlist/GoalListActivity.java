package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Loads up a list of all the <code>Goal</code> objects where the user can clear
 * the list, add new <code>Goal</code> objects, or view specific ones in more detail
 *
 * @author Vincent Nguyen
 */
public class GoalListActivity extends AppCompatActivity {
    private DBHelper database;
    private List<Goal> goalsList;
    private GoalListAdapter goalsListAdapter;
    private ListView goalsListView;

    private AnimationDrawable frameAnim;
    private Animation rotateAnim;
    private Animation shakeAnim;
    private AnimationDrawable customAnim;

    private Sensor accelerometer;
    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;

    private ImageView goalsListImageView;

    /**
     *
     * @param savedInstanceState The current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        database = new DBHelper(this);

        database.addGoal(new Goal("Example1", "Example1", "Example1",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example2", "Example2", "Example2",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example3", "Example3", "Example3",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example4", "Example4", "Example4",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example5", "Example5", "Example5",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example6", "Example6", "Example6",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example7", "Example7", "Example7",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example8", "Example8", "Example8",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example9", "Example9", "Example9",
                getUriToResource(this, R.mipmap.ic_launcher), false));
        database.addGoal(new Goal("Example10", "Example10", "Example10",
                getUriToResource(this, R.mipmap.ic_launcher), false));

        goalsList = database.getAllGoals();
        goalsListAdapter = new GoalListAdapter(this, R.layout.list_item, goalsList);

        goalsListView = (ListView) findViewById(R.id.goalsListListView);
        goalsListView.setAdapter(goalsListAdapter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            /**
             * When a 3D motion that the sensors constitute as a shake has been detected,
             * the SecretActivity is loaded
             */
            @Override
            public void onShake() {
                startActivity(new Intent(GoalListActivity.this, SecretActivity.class));
            }
        });

        goalsListImageView = (ImageView) findViewById(R.id.goalsListImageView);
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
     * all the sensors that monitor device movements and g-forces are then paused
     * to preserve battery life and RAM
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }

    /**
     * Takes the specific <code>Goal</code> that the user taps on and opens
     * up the details menu where the user can view more specific information
     * in regards to that goal
     * @param view The <code>Goal</code> ListView
     */
    public final void viewGoalDetails(final View view) {
        if (view instanceof LinearLayout) {
            final Goal SELECTED_GOAL = (Goal) view.getTag();
            Log.i("Bucket List", SELECTED_GOAL.toString());

            Intent listIntent = new Intent(this, GoalDetailsActivity.class);
            listIntent.putExtra("Selected", SELECTED_GOAL);
            startActivity(listIntent);
        }
        else
            Toast.makeText(this, "Error loading selected goal.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Allows the user to add a new goal by loading up the add new goal page
     * @param view The add <code>Goal</code> button
     */
    public final void addGoal(final View view) {
        startActivity(new Intent(GoalListActivity.this, AddNewGoalActivity.class));
    }

    /**
     * Deletes all the current <code>Goal</code> objects inside the list and databases.
     * @param view The clear button to delete all the <code>Goal</code> objects
     */
    public final void clearAllGoals(final View view) {
        if (!goalsList.isEmpty()) {
            goalsList.clear();
            database.deleteAllGoals();
            goalsListAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(this, "Goals List is already empty.", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param view
     */
    public final void toggleFrameAnim(final View view) {
        // 1.) Programmatically set the background of the ImageView to @drawable/light1
        goalsListImageView.setBackgroundResource(R.drawable.frame_anim);

        // 2.) Associate the frameAnim with the animation in XML
        frameAnim = (AnimationDrawable) goalsListImageView.getBackground();

        // 3.) Start the frame animation if it isn't running and stop it if it already is
        if (frameAnim.isRunning())
            frameAnim.stop();
        else
            frameAnim.start();
    }

    /**
     *
     * @param view
     */
    public final void toggleRotateAnim(final View view) {
        if (rotateAnim != null && rotateAnim.hasStarted()) {
            goalsListImageView.clearAnimation();
            rotateAnim = null;
        }
        else {
            rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
            goalsListImageView.startAnimation(rotateAnim);
        }
    }

    /**
     *
     * @param view
     */
    public final void toggleShakeAnim(final View view) {
        if (shakeAnim != null && shakeAnim.hasStarted()) {
            goalsListImageView.clearAnimation();
            shakeAnim = null;
        }
        else {
            shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
            goalsListImageView.startAnimation(shakeAnim);
        }
    }

    /**
     *
     * @param view
     */
    public final void toggleGreyscale(final View view) {
        goalsListImageView.setBackgroundResource(R.drawable.color_anim);

        customAnim = (AnimationDrawable) goalsListImageView.getBackground();

        if (customAnim.isRunning())
            customAnim.stop();
        else
            customAnim.start();
    }

    /**
     * Get uri to any resource type within an Android Studio project. Method is public static to
     * allow other classes to use it as a helper function
     * @param context The current context
     * @param resID The resource identifier for drawable
     * @return Uri to resource by given id
     * @throws Resources.NotFoundException If the given resource id does not exist.
     */
    public final static Uri getUriToResource(@NonNull Context context, @AnyRes final int resID)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package */
        Resources res = context.getResources();
        /** return URI */
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resID)
                + '/' + res.getResourceTypeName(resID)
                + '/' + res.getResourceEntryName(resID));
    }
}