package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

/**
 *
 */
public class GoalListActivity extends AppCompatActivity {
    private DBHelper database;
    private List<Goal> goalsList;
    private GoalListAdapter goalsListAdapter;
    private ListView goalsListView;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        database = new DBHelper(this);

        goalsList = database.getAllGoals();
        goalsListAdapter = new GoalListAdapter(this, R.layout.list_item, goalsList);

        goalsListView = (ListView) findViewById(R.id.goalsListListView);
        goalsListView.setAdapter(goalsListAdapter);
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