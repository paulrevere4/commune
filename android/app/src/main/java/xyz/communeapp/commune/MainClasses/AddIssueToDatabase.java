package xyz.communeapp.commune.MainClasses;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Adds an issue to the database
 */
public class AddIssueToDatabase {

    private Issue mIssue;
    private DatabaseReference mGroupRef;

    /**
     * Constructor
     *
     * @param issue    issue to be written to database
     * @param groupRef database reference to the group the issue belongs to
     */
    public AddIssueToDatabase(Issue issue, DatabaseReference groupRef) {
        this.mIssue = issue;
        this.mGroupRef = groupRef;
    }

    /**
     * Adds the issue to the database
     */
    public void add() {
        mIssue.setIssueID(mGroupRef.push().getKey());
        mIssue.setIssueRef(mGroupRef.child(mIssue.getIssueID()).getRef());
        mIssue.getIssueRef().child("Name").setValue(mIssue.getName());
        mIssue.getIssueRef().child("DueDate").setValue(mIssue.getDueDate());
        mIssue.getIssueRef().child("Description").setValue(mIssue.getDescription());
        mIssue.getIssueRef().child("AssignedTo").child("UserID").setValue(mIssue.getAssigedToUID());
        mIssue.getIssueRef().child("AssignedTo").child("UserName").setValue(mIssue
                .getAssigedToName());
        mIssue.getIssueRef().child("Completed").setValue(mIssue.getStatus());
    }

    /**
     * Adds the issue to a particular user database reference if it is assigned to a person
     */
    public void addIssueToUser() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(mIssue.getAssigedToUID()).child("Issues").child(mIssue.getIssueID())
                .getRef();
        userRef.child("Name").setValue(mIssue.getName());
        userRef.child("DueDate").setValue(mIssue.getDueDate());
        userRef.child("Description").setValue(mIssue.getDescription());
        userRef.child("Completed").setValue(mIssue.getStatus());
        userRef.child("GroupID").setValue(mIssue.getGroupID());
    }
}
