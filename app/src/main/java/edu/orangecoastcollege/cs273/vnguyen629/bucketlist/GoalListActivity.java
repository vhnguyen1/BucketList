package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

    private Sensor accelerometer;
    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;

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