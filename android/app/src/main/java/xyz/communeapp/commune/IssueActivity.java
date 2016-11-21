package xyz.communeapp.commune;

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

public class IssueActivity extends AppCompatActivity implements MarkIssueCompleteDialog.NoticeDialogListener {

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

    private void setCalculatedValue(float currentValue, float newValue, DatabaseReference ref, ValueEventListener listener){
        ref.removeEventListener(listener);
        float newTotal = currentValue+newValue;
        FirebaseDatabase.getInstance().getReference().child("Groups").child(mIssueGroupID).child("MonetaryContributions").child(mUserUID).setValue(newTotal);
    }

    private void setValue(DatabaseReference ref, float value, ValueEventListener listener){
        ref.setValue(value);
        ref.removeEventListener(listener);
    }

    private void addMonetaryValue(final String value){
        final float money = Float.parseFloat(value);

        //FirebaseDatabase.getInstance().getReference().child("Groups").child(mIssueGroupID).child("MonetaryContributions").child(mUserUID).setValue(money);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Groups").child(mIssueGroupID).child("MonetaryContributions").child(mUserUID).getRef();


        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    setValue(ref,money,this);
                }else{
                    String current = dataSnapshot.getValue().toString();
                    float currentF = Float.parseFloat(current);
                    setCalculatedValue(currentF,money, ref, this);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listener);

    }

    public void onDialogPositiveClick(DialogFragment dialog, String value){
        mUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        addMonetaryValue(value);
        mIssueRef.child("Completed").setValue("True");
        if(mIssueAssignedTo != null){
            FirebaseDatabase.getInstance().getReference().child("Users").child(mUserUID).child("Issues").child(mIssueID).child("Completed").setValue("True");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mIssueID = getIntent().getStringExtra("ISSUE_ID");

        mIssueGroupID = getIntent().getStringExtra("GROUP_ID");

        mIssueNameTextView = (TextView) findViewById(R.id.issue_name_textView);
        mIssueDueDateTextView = (TextView) findViewById(R.id.due_date_textView);
        mIssueDescriptionTextView = (TextView) findViewById(R.id.description_textView);
        mIssueAssignedToTextView = (TextView) findViewById(R.id.assigned_to_textView);
        mIssueGroupTextView = (TextView) findViewById(R.id.issue_group_textView);
        mMarkIssueCompleteButton = (Button) findViewById(R.id.mark_as_complete_button);

        mGroupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mIssueGroupID).getRef();
        mIssueRef = mGroupRef.child("Issues").child(mIssueID).getRef();

        if(mGroupRef != null){
            mGroupRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mIssueGroup = dataSnapshot.child("Name").getValue().toString();
                    mIssueGroupTextView.setText("Group: "+mIssueGroup);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if(mIssueRef != null){
            mIssueRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mIssueName = dataSnapshot.child("Name").getValue().toString();
                    mIssueNameTextView.setText("Issue Name: "+mIssueName);

                    mIssueDueDate = dataSnapshot.child("DueDate").getValue().toString();
                    mIssueDueDateTextView.setText("Due Date: "+mIssueDueDate);

                    mIssueDescription = dataSnapshot.child("Description").getValue().toString();
                    mIssueDescriptionTextView.setText("Description: "+mIssueDescription);

                    mIssueAssignedTo = dataSnapshot.child("AssignedTo").child("UserName").getValue().toString();
                    mIssueAssignedToTextView.setText("Assigned To: "+mIssueAssignedTo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        mMarkIssueCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarkIssueCompleteDialog dialog = new MarkIssueCompleteDialog();
                dialog.show(getSupportFragmentManager(), "MarkIssueCompleteDialogFragment");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }
}
