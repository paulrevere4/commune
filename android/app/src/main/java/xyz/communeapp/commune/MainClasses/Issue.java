package xyz.communeapp.commune.MainClasses;

import com.google.firebase.database.DatabaseReference;

/**
 * Holds information about an issue in the app
 */
public class Issue {

    private String mIssueID; // Issue unique ID
    private String mName;    // Issue name
    private String mDueDate; // Issue due date
    private String mDescription; // Issue details
    private String mAssignedToName; // Name of person the issue is assigned to
    private String mAssignedToUID; // UID of the person the issue is assigned to
    private String mGroupID;// Unique ID of the group the issue belongs to
    private String mStatus; // Status of the issue
    private DatabaseReference mIssueDatabaseRef; // Issue database reference

    /**
     * Constructor
     */
    public Issue() {
        this.mStatus = "False";
    }

    /**
     * Gets the issue ID
     *
     * @return The issue ID
     */
    public String getIssueID() {
        return this.mIssueID;
    }

    /**
     * Sets the issue ID
     *
     * @param ID The issue ID
     */
    public void setIssueID(String ID) {
        this.mIssueID = ID;
    }

    /**
     * Gets the issue name
     *
     * @return the issue name
     */
    public String getName() {
        return this.mName;
    }

    /**
     * Sets the issue name
     *
     * @param name the issue name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Gets the issue due date
     *
     * @return string of the due date
     */
    public String getDueDate() {
        return this.mDueDate;
    }

    /**
     * Sets the issue due date
     *
     * @param dueDate string of the due date
     */
    public void setDueDate(String dueDate) {
        this.mDueDate = dueDate;
    }

    /**
     * Gets the issue reference
     *
     * @return The DB reference
     */
    public DatabaseReference getIssueRef() {
        return this.mIssueDatabaseRef;
    }

    /**
     * Sets the issue reference
     *
     * @param ref The DB reference
     */
    public void setIssueRef(DatabaseReference ref) {
        this.mIssueDatabaseRef = ref;
    }

    /**
     * Gets the issue description
     *
     * @return String of the issue description
     */
    public String getDescription() {
        return this.mDescription;
    }

    /**
     * Sets the issue description
     *
     * @param description String of the issue description
     */
    public void setDescription(String description) {
        this.mDescription = description;
    }

    /**
     * Sets the name of the user the issue is assigned to
     *
     * @param name The name of the user
     */
    public void setAssignedToName(String name) {
        this.mAssignedToName = name;
    }

    /**
     * Gets the name of the user the issue is assigned to
     *
     * @return The name of the user
     */
    public String getAssigedToName() {
        return this.mAssignedToName;
    }

    /**
     * Sets the UID of the user the issue is assigned to
     *
     * @param UID The UID of the user
     */
    public void setAssignedToUID(String UID) {
        this.mAssignedToUID = UID;
    }

    /**
     * Gets the UID of the user the issue is assigned to
     *
     * @return The UID of the user
     */
    public String getAssigedToUID() {
        return this.mAssignedToUID;
    }

    /**
     * Gets the ID of the group
     *
     * @return The ID of the group
     */
    public String getGroupID() {
        return this.mGroupID;
    }

    /**
     * Sets the group ID
     *
     * @param groupID The ID of the group
     */
    public void setGroupID(String groupID) {
        this.mGroupID = groupID;
    }

    /**
     * Gets the status of the issue
     *
     * @return The status of the issue
     */
    public String getStatus() {
        return this.mStatus;
    }

    /**
     * Sets the status of the issue
     *
     * @param status The status of the issue
     */
    public void setStatus(String status) {
        this.mStatus = status;
    }
}
