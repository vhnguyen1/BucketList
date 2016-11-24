package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The <code>Goal</code> class maintains information about a goal in the bucket list,
 * including its unique id number, title, description, recorded date written,
 * current status, and image.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public final class Goal implements Parcelable {
    private int mID;
    private String mTitle;
    private String mDescription;
    private String mDateWritten;
    private Uri mGoalImage;
    private boolean mCompleted;

    private static final String DEFAULT = "N/A";

    /**
     * Parcelable constructor that creates a new <code>Goal</code> object
     * containing information in regards to its unique id number, title,
     * description, recorded date written, current status, and image.
     * @param source Parcelable cursor storing the data for the <code>Goal</code>
     */
    private Goal(final Parcel source) {
        this.mID = source.readInt();
        this.mTitle = source.readString();
        this.mDescription = source.readString();
        this.mDateWritten = source.readString();
        this.mCompleted = ((source.readString().equals("Completed"))? true : false);
        this.mGoalImage = Uri.parse(source.readString());
    }

    /**
     * Overloaded constructor that creates a new <code>Goal</code> object
     * containing information in regards to its unique id number, title,
     * description, recorded date written, current status, and image.
     * @param id The goals unique ID
     * @param title The goals title
     * @param description The description about the goal
     * @param dateWritten The goals date in which it was written
     * @param imageURI The URI to get the image
     * @param status Whether or not the goal is completed
     */
    public Goal(final int id, final String title, final String description,
                 final String dateWritten, final Uri imageURI, final boolean status) {
        this.mID = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mDateWritten = dateWritten;
        this.mGoalImage = imageURI;
        this.mCompleted = status;
    }

    /**
     * Default constructor that creates a new <code>Goal</code> object
     * initializing its contents to default values. Unique ID is not
     * manipulated in this constructor.
     */
    public Goal() {
        this(-1, DEFAULT, DEFAULT, DEFAULT, null, false);
    }

    /**
     * Overloaded constructor that creates a new <code>Goal</code> object
     * containing information in regards to its title, description,
     * recorded date written, current status, and image. Unique ID is not
     * manipulated in this constructor.
     * @param title The goals title
     * @param description The description about the goal
     * @param dateWritten The goals date in which it was written
     * @param imageURI The URI to get the image
     * @param status Whether or not the goal is completed
     */
    public Goal(final String title, final String description, final String dateWritten,
                final Uri imageURI, final boolean status) {
        this(-1, title, description, dateWritten, imageURI, status);
    }

    /**
     * Copy Constructor that creates a new <code>Goal</code> object
     * containing information in regards to its title, description,
     * recorded date written, current status, and image. Unique ID is not
     * manipulated in this constructor.
     * @param other The other <code>Goal</code> object to copy data from
     */
    public Goal(final Goal other) {
        this(-1, other.mTitle, other.mDescription, other.mDateWritten, other.mGoalImage,
                other.mCompleted);
    }

    /**
     * Writes all of the <code>Goal</code> objects data into a parcelable cursor
     * object
     * @param parcel The parcelable cursor object to receive the data
     * @param flags
     */
    @Override
    public final void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeInt(this.mID);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mDescription);
        parcel.writeString((this.mCompleted)? "Completed" : "Incomplete");
        parcel.writeString(this.mDateWritten);
        parcel.writeString(this.mGoalImage.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public final int describeContents() {
        return 0;
    }

    /**
     *
     */
    public static final Parcelable.Creator<Goal> CREATOR = new Parcelable.Creator<Goal>() {
        /**
         *
         * @param source
         * @return
         */
        @Override
        public final Goal createFromParcel(final Parcel source) {
            return new Goal(source);
        }

        /**
         *
         * @param size
         * @return
         */
        @Override
        public final Goal[] newArray(final int size) {
            return new Goal[size];
        }
    };

    /**
     * Creates a String representation of a given <code>Goal</code>,
     * with all member variables displayed.
     * @return The string representation of the <code>Goal</code> object
     */
    public final String toString() {
        return "Goal{" +
                "Id=" + this.mID +
                ", Title='" + this.mTitle + '\'' +
                ", Description='" + this.mDescription + '\'' +
                ", Date=" + this.mDateWritten + '\'' +
                ", mStatus=" + ((this.mCompleted)? "Found" : "Missing") +
                '}';
    }

    /**
     *
     * @return
     */
    public final int getID() {
        return this.mID;
    }

    /**
     *
     * @return
     */
    public final String getTitle() {
        return this.mTitle;
    }

    /**
     *
     * @return
     */
    public final String getDescription() {
        return this.mDescription;
    }

    /**
     *
     * @return
     */
    public final String getDateWritten() {
        return this.mDateWritten;
    }

    /**
     *
     * @return
     */
    public final Uri getGoalImageURI() {
        return this.mGoalImage;
    }

    /**
     *
     * @return
     */
    public final boolean getStatus() {
        return this.mCompleted;
    }

    /**
     *
     * @param id
     */
    private final void setID(final int id) {
        this.mID = id;
    }

    /**
     *
     * @param title
     */
    public final void setTitle(final String title) {
        this.mTitle = title;
    }

    /**
     *
     * @param description
     */
    public final void setDescription(final String description) {
        this.mDescription = description;
    }

    /**
     *
     * @param dateWritten
     */
    public final void setDateWritten(final String dateWritten) {
        this.mDateWritten = dateWritten;
    }

    /**
     *
     * @param imageURI
     */
    public final void setGoalImage(final Uri imageURI) {
        this.mGoalImage = imageURI;
    }

    /**
     *
     * @param status
     */
    public final void setTitle(final boolean status) {
        this.mCompleted = status;
    }
}