package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide a custom adapter for the <code>Goal</code> list.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public final class GoalListAdapter extends ArrayAdapter<Goal> {
    private int mResourceId;
    private Context mContext;
    private List<Goal> mGoalsList = new ArrayList<>();

    private LinearLayout listLinearLayout;
    private ImageView listImageView;
    private TextView listTitleTextView;
    private TextView listDateWrittenTextView;

    /**
     * Creates a new <code>GoalListAdapter</code> given a mContext, resource id
     * and list of lost items.
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param goals The list of <code>Goal</code> objects to display
     */
    public GoalListAdapter(Context c, final int rId, final List<Goal> goals) {
        super(c, rId, goals);
        this.mContext = c;
        this.mResourceId = rId;
        this.mGoalsList = goals;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the <code>Goal</code> selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public final View getView(final int pos, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) this.mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View VIEW = inflater.inflate(this.mResourceId, null);

        listLinearLayout = (LinearLayout) VIEW.findViewById(R.id.listLinearLayout);
        listImageView = (ImageView) VIEW.findViewById(R.id.listImageView);
        listTitleTextView = (TextView) VIEW.findViewById(R.id.listTitleTextView);
        listDateWrittenTextView = (TextView) VIEW.findViewById(R.id.listDateWrittenTextView);

        final Goal GOAL = this.mGoalsList.get(pos);
        listLinearLayout.setTag(GOAL);

        final String TITLE = GOAL.getTitle();
        final String DATE_LOST = GOAL.getDateWritten();
        final Uri IMAGE_URI = GOAL.getGoalImageURI();

        listImageView.setImageURI(IMAGE_URI);
        listTitleTextView.setText(TITLE);
        listDateWrittenTextView.setText(DATE_LOST);

        return VIEW;
    }
}