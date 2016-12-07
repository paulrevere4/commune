package xyz.communeapp.commune.Activities.IssueActivity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import xyz.communeapp.commune.Dialogs.MarkIssueCompleteDialog;
import xyz.communeapp.commune.R;

public class IssueActivity extends AppCompatActivity implements MarkIssueCompleteDialog
        .NoticeDialogListener {

    private String mIssueID;
    private String mUserUID;
    private DatabaseReference mIssueRef;
    private DatabaseReference mGroupRef;
    private String mIssueGroupID;
    private TextView mIssueNameTextView;
    private TextView mIssueDueDateTextView;
    private TextView mIssueDescriptionTextView;
    private TextView mIssueAssignedToTextView;
    private TextView mIssueGroupTextView;
    private Button mMarkIssueCompleteButton;
    private String mIssueName;
    private String mIssueDueDate;
    private String mIssueDescription;
    private String mIssueAssignedTo;
    private String mIssueGroup;

    /**
     * Calculates a new contribution value by adding previous contribution and current contribution
     *
     * @param currentValue current contribution
     * @param newValue     new contribution
     * @param ref          database reference to the monetary contributions tree under the group
     * @param listener     a listening object listening for changes in the database
     */
    private void setCalculatedValue(float currentValue, float newValue, DatabaseReference ref,
                                    ValueEventListener listener) {
        ref.removeEventListener(listener);
        float newTotal = currentValue + newValue;
        FirebaseDatabase.getInstance().getReference().child("Groups").child(mIssueGroupID).child
                ("MonetaryContributions").child(mUserUID).setValue(newTotal);
    }

    /**
     * Set the monetary value if one does not already exist
     *
     * @param ref      database reference to the monetary contributions tree under the group
     * @param value    value to be written
     * @param listener a listening object listening for changes in the database
     */
    private void setValue(DatabaseReference ref, float value, ValueEventListener listener) {
        ref.setValue(value);
        ref.removeEventListener(listener);
    }

    /**
     * Adds a monetary for the user that completes an issue
     *
     * @param value dollar value for the issue
     */
    private void addMonetaryValue(final String value) {
        final float money = Float.parseFloat(value);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child
                ("Groups").child(mIssueGroupID).child("MonetaryContributions").child(mUserUID)
                .getRef();

        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    setValue(ref, money, this);
                } else {
                    String current = dataSnapshot.getValue().toString();
                    float currentF = Float.parseFloat(current);
                    setCalculatedValue(currentF, money, ref, this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listener);
    }

    /**
     * Deletes an issue after it is marked complete
     */
    private void deleteIssue() {
        mUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (!mIssueAssignedTo.equals("NA")) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(mUserUID).child
                    ("Issues").child(mIssueID).removeValue();
        }
        mIssueRef.removeValue();
        recreate();
    }

    /**
     * A callback function that is triggered when user selects yes to adding a monetary value
     *
     * @param dialog the dialog being shown the user
     * @param value  the value the user enters
     */
    public void onDialogPositiveClick(DialogFragment dialog, String value) {
        if (value.isEmpty()) {
            addMonetaryValue("0");
        } else {
            addMonetaryValue(value);
        }
        deleteIssue();
    }

    /**
     * A callback function that is triggered when user selects no to adding a monetary value
     *
     * @param dialog
     */
    public void onDialogNegativeClick(DialogFragment dialog) {
        deleteIssue();
    }

    /**
     * A callback function triggered during activity creation
     *
     * @param savedInstanceState background information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get messages sent by previous activity
        mIssueID = getIntent().getStringExtra("ISSUE_ID");
        mIssueGroupID = getIntent().getStringExtra("GROUP_ID");

        // Get UI elements
        mIssueNameTextView = (TextView) findViewById(R.id.issue_name_textView);
        mIssueDueDateTextView = (TextView) findViewById(R.id.due_date_textView);
        mIssueDescriptionTextView = (TextView) findViewById(R.id.description_textView);
        mIssueAssignedToTextView = (TextView) findViewById(R.id.assigned_to_textView);
        mIssueGroupTextView = (TextView) findViewById(R.id.issue_group_textView);
        mMarkIssueCompleteButton = (Button) findViewById(R.id.mark_as_complete_button);

        // Get database references
        mGroupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child
                (mIssueGroupID).getRef();
        mIssueRef = mGroupRef.child("Issues").child(mIssueID).getRef();

        // Read database
        if (mGroupRef != null) {
            mGroupRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mIssueGroup = dataSnapshot.child("Name").getValue().toString();
                    mIssueGroupTextView.setText("Group: " + mIssueGroup);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        // Get values from database and assign to variables
        if (mIssueRef != null) {
            mIssueRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mIssueName = dataSnapshot.child("Name").getValue().toString();
                    mIssueNameTextView.setText("Issue Name: " + mIssueName);
                    mIssueDueDate = dataSnapshot.child("DueDate").getValue().toString();
                    mIssueDueDateTextView.setText("Due Date: " + mIssueDueDate);
                    mIssueDescription = dataSnapshot.child("Description").getValue().toString();
                    mIssueDescriptionTextView.setText("Description: " + mIssueDescription);
                    mIssueAssignedTo = dataSnapshot.child("AssignedTo").child("UserName")
                            .getValue().toString();
                    mIssueAssignedToTextView.setText("Assigned To: " + mIssueAssignedTo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // Set on-click-listener to complete issue button
        mMarkIssueCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and show dialog to user to add monetary value to issue
                MarkIssueCompleteDialog dialog = new MarkIssueCompleteDialog();
                dialog.show(getSupportFragmentManager(), "MarkIssueCompleteDialogFragment");
            }
        });
    }

    /**
     * Callback function back press from menu
     *
     * @param item item that was selected in menu
     * @return return success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}
