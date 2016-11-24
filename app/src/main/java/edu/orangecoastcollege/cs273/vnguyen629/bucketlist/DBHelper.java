package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides the necessary functions and tools to put information
 * regarding reported <code>Goal</code> objects inside an SQLite database while
 * providing various different functions such as adding, upgrading
 * and modifications.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
final class DBHelper extends SQLiteOpenHelper {
    private Context mContext;

    static final String DATABASE_NAME = "BucketList";
    private static final int DATABASE_VERSION = 1;

    private static final String GOALS_TABLE = "Goals";
    private static final String GOAL_KEY_FIELD_ID = "id";
    private static final String FIELD_GOAL_TITLE = "title";
    private static final String FIELD_GOAL_DESCRIPTION = "description";
    private static final String FIELD_GOAL_DATE_WRITTEN = "date_written";
    private static final String FIELD_GOAL_IMAGE_URI = "image_uri";
    private static final String FIELD_GOAL_STATUS = "status";

    /**
     * Creates a new local SQL database for <code>Goal</code> objects
     * @param context The activity to store the data
     */
    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    /**
     * Creates a new local <code>Goal</code> SQL database
     * @param db The local <code>Goal</code> database
     */
    @Override
    public void onCreate (final SQLiteDatabase db){
        String goalsTable = "CREATE TABLE " + GOALS_TABLE + "("
                + GOAL_KEY_FIELD_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_GOAL_TITLE + " TEXT, "
                + FIELD_GOAL_DESCRIPTION + " TEXT, "
                + FIELD_GOAL_DATE_WRITTEN + " TEXT, "
                + FIELD_GOAL_IMAGE_URI + " TEXT, "
                + FIELD_GOAL_STATUS + " INTEGER" + ")";
        db.execSQL(goalsTable);
    }

    /**
     * Upgrades the current <code>Goal</code> database for newer versions
     * @param db The <code>Goal</code> database
     * @param oldVersion The previous database version number
     * @param newVersion The new database version number
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE);
        onCreate(db);
    }

    /**
     * Adds a new <code>Goal</code> object into the current database
     * @param newGoal The new <code>Goal</code> to be added to the database
     */
    public final void addGoal(final Goal newGoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        final String TITLE = newGoal.getTitle();
        final String DESCRIPTION = newGoal.getDescription();
        final String DATE_LOST = newGoal.getDateWritten();
        final String IMAGE_URI = newGoal.getGoalImageURI().toString();
        final int STATUS = ((newGoal.getStatus())? 1 : 0);

        values.put(FIELD_GOAL_TITLE, TITLE);
        values.put(FIELD_GOAL_DESCRIPTION, DESCRIPTION);
        values.put(FIELD_GOAL_DATE_WRITTEN, DATE_LOST);
        values.put(FIELD_GOAL_IMAGE_URI, IMAGE_URI);
        values.put(FIELD_GOAL_STATUS, STATUS);

        db.insert(GOALS_TABLE, null, values);
        db.close();
    }

    /**
     * Returns a list of all the <code>Goal</code> objects inside the database
     * @return The list of all the <code>Goal</code> objects
     */
    public final ArrayList<Goal> getAllGoals() {
        ArrayList<Goal> goalsArrayList = new ArrayList<>();

        final SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.query(
                GOALS_TABLE,
                new String[]{GOAL_KEY_FIELD_ID, FIELD_GOAL_TITLE, FIELD_GOAL_DESCRIPTION,
                        FIELD_GOAL_DATE_WRITTEN, FIELD_GOAL_IMAGE_URI, FIELD_GOAL_STATUS},
                null, null, null, null, null, null );

        if (cursor.moveToFirst()){
            do {
                final int ITEM_ID = cursor.getInt(0);
                final String TITLE = cursor.getString(1);
                final String DESCRIPTION = cursor.getString(2);
                final String DATE_WRITTEN = cursor.getString(3);
                final Uri IMAGE_URI = Uri.parse(cursor.getString(4));
                final boolean STATUS = ((cursor.getInt(5) == 1)? true : false);

                goalsArrayList.add(new Goal(ITEM_ID, TITLE, DESCRIPTION, DATE_WRITTEN,
                        IMAGE_URI, STATUS));

            } while (cursor.moveToNext());
        }

        DB.close();
        return goalsArrayList;
    }

    /**
     * Retrieves a specified <code>Goal</code> from the database
     * @param id The unique id of the <code>Goal</code>
     * @return The <code>Goal</code> with the matching unique ID
     */
    public final Goal getGoal(final int id) {
        final SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.query(
                GOALS_TABLE,
                new String[]{GOAL_KEY_FIELD_ID, FIELD_GOAL_TITLE, FIELD_GOAL_DESCRIPTION,
                        FIELD_GOAL_DATE_WRITTEN, FIELD_GOAL_IMAGE_URI, FIELD_GOAL_STATUS},
                GOAL_KEY_FIELD_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        final int ITEM_ID = cursor.getInt(0);
        final String TITLE = cursor.getString(1);
        final String DESCRIPTION = cursor.getString(2);
        final String DATE_WRITTEN = cursor.getString(3);
        final Uri IMAGE_URI = Uri.parse(cursor.getString(4));
        final boolean STATUS = ((cursor.getInt(5) == 1)? true : false);

        final Goal ITEM = new Goal(ITEM_ID, TITLE, DESCRIPTION, DATE_WRITTEN,
                IMAGE_URI, STATUS);

        DB.close();
        return ITEM;
    }

    /**
     * Applies changes/updates to a <code>Goal</code>
     * @param goal <code>Goal</code> to be updated in the database
     */
    public final void updateGoal(final Goal goal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        final String TITLE = goal.getTitle();
        final String DESCRIPTION = goal.getDescription();
        final String DATE_LOST = goal.getDateWritten();
        final String IMAGE_URI = goal.getGoalImageURI().toString();
        final int STATUS = ((goal.getStatus())? 1 : 0);

        values.put(FIELD_GOAL_TITLE, TITLE);
        values.put(FIELD_GOAL_DESCRIPTION, DESCRIPTION);
        values.put(FIELD_GOAL_DATE_WRITTEN, DATE_LOST);
        values.put(FIELD_GOAL_IMAGE_URI, IMAGE_URI);
        values.put(FIELD_GOAL_STATUS, STATUS);

        db.update(GOALS_TABLE, values, GOAL_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(goal.getID())});
        db.close();
    }

    /**
     * Delete all the <code>Goal</code> objects inside the current database
     */
    public final void deleteAllGoals() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GOALS_TABLE, null, null);
        db.close();
    }

    /**
     * Imports/reads all the  <code>Goal</code> object data from a CSV file to populate
     * the database with.
     * @param csvFileName The name of the file to import data from
     * @return Whether or not the file data was open and imported successfully
     */
    public final boolean importGoalsFromCSV(final String csvFileName) {
        final AssetManager MANAGER = mContext.getAssets();
        InputStream inStream;
        try {
            inStream = MANAGER.open(csvFileName);
        } catch (final IOException ERR) {
            ERR.printStackTrace();
            return false;
        }

        final BufferedReader BUFFER = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = BUFFER.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 4) {
                    Log.d("Bucket List", "Skipping Bad CSV Row: "
                            + Arrays.toString(fields));
                    continue;
                }

                final int ID = Integer.parseInt(fields[0].replaceAll("\\s+",""));
                final String TITLE = fields[1].trim();
                final String DESCRIPTION = fields[2].trim();
                final String DATE_LOST = fields[3].trim();
                final Uri IMAGE_URI = Uri.parse(fields[5].replaceAll("\\s+",""));
                final boolean STATUS = ((fields[5].replaceAll("\\s+","")
                        .equals("Found"))? true : false);

                addGoal(new Goal(ID, TITLE, DESCRIPTION, DATE_LOST, IMAGE_URI, STATUS));
            }
        } catch (final IOException ERR) {
            ERR.printStackTrace();
            return false;
        }
        return true;
    }
}