package xyz.communeapp.commune.Classes;

import com.google.firebase.database.DatabaseReference;

public class Issue {

    private String mIssueID; // Issue unique ID
    private String mName;    // Issue name
    private String mDueDate; // Issue due date
    private String mDescription; // Issue details
    private String mAssignedToName; // Name of person the issue is assigned to
    private String mAssignedToUID; // UID of the person the issue is assigned to
    private String mGroupID;// Unique ID of the group the issue belongs to
    private boolean mStatus; // Status of the issue
    private DatabaseReference mIssueDatabaseRef; // Issue database reference

    public Issue() {
        this.mStatus = false;
    }

    public String getIssueID() {
        return this.mIssueID;
    }

    public void setIssueID(String ID) {
        this.mIssueID = ID;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDueDate() {
        return this.mDueDate;
    }

    public void setDueDate(String dueDate) {
        this.mDueDate = dueDate;
    }

    public DatabaseReference getIssueRef() {
        return this.mIssueDatabaseRef;
    }

    public void setIssueRef(DatabaseReference ref) {
        this.mIssueDatabaseRef = ref;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setAssignedToName(String name) {
        this.mAssignedToName = name;
    }

    public String getAssigedToName() {
        return this.mAssignedToName;
    }

    public void setAssignedToUID(String UID) {
        this.mAssignedToUID = UID;
    }

    public String getAssigedToUID() {
        return this.mAssignedToUID;
    }

    public String getGroupID() {
        return this.mGroupID;
    }

    public void setGroupID(String groupID) {
        this.mGroupID = groupID;
    }

    public boolean getStatus() {
        return this.mStatus;
    }

    public void setStatus(boolean status) {
        this.mStatus = status;
    }
}
