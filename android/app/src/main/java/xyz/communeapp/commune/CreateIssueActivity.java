package xyz.communeapp.commune;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateIssueActivity extends AppCompatActivity {

    private String mGroupID;
    private String mAssignedToUID;
    private DatabaseReference mGroupIssuesRef;
    private DatabaseReference mGroupMembersRef;
    private EditText mNameEditText;
    private EditText mDueDateEditText;
    private EditText mIssueDescription;
    private EditText mMemberSelectEditText;
    private Button mCreateIssueButton;
    private ArrayAdapter<String> mMembersAdapter;
    private ArrayList<String> mGroupMemberNames;
    private ArrayList<String> mGroupMemberUIDs;

    private void writeIssueToDatabase(Issue issue){
        AddIssueToDatabase addToDatabase = new AddIssueToDatabase(issue, mGroupIssuesRef);
        addToDatabase.add();
        if(!mMemberSelectEditText.getText().toString().isEmpty()){
            addToDatabase.addIssueToUser();
        }
        finish();
    }

    private void createIssue(){
        Issue issue = new Issue();
        issue.setName(mNameEditText.getText().toString());
        issue.setGroupID(mGroupID);

        if(mDueDateEditText.getText().toString().isEmpty())
            issue.setDueDate("NA");
        else
            issue.setDueDate(mDueDateEditText.getText().toString());

        if(mIssueDescription.getText().toString().isEmpty())
            issue.setDescription("NA");
        else
            issue.setDescription(mIssueDescription.getText().toString());

        if(mMemberSelectEditText.getText().toString().isEmpty()){
            issue.setAssignedToName("NA");
            issue.setAssignedToUID("NA");
        }else{
            issue.setAssignedToName(mMemberSelectEditText.getText().toString());
            issue.setAssignedToUID(mAssignedToUID);
        }
        writeIssueToDatabase(issue);
    }

    private void updateLabel(Calendar myCalendar) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mDueDateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);

        mGroupID = getIntent().getStringExtra("GROUP_ID");
        mGroupIssuesRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID).child("Issues").getRef();
        mGroupMembersRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID).child("Members");
        mNameEditText = (EditText) findViewById(R.id.issue_name_editText);
        mDueDateEditText = (EditText) findViewById(R.id.due_date_editText);
        mMemberSelectEditText = (EditText) findViewById(R.id.select_user_editText);
        mIssueDescription = (EditText) findViewById(R.id.issue_description_editText);
        mCreateIssueButton = (Button) findViewById(R.id.create_issue_button);

        mCreateIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNameEditText.getText().toString().isEmpty()){
                    Toast.makeText(CreateIssueActivity.this, "Name is required for issue", Toast.LENGTH_SHORT).show();
                }else {
                    createIssue();
                }
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }
        };

        mDueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateIssueActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mGroupMemberNames = new ArrayList<>();
        mGroupMemberUIDs = new ArrayList<>();
        mMembersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mGroupMemberNames);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                mGroupMemberNames.add(dataSnapshot.getValue().toString());
                mGroupMemberUIDs.add(dataSnapshot.getKey());
                mMembersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                int i = mGroupMemberNames.indexOf(dataSnapshot.getValue().toString());
                int j = mGroupMemberUIDs.indexOf(dataSnapshot.getKey());
                mGroupMemberNames.remove(i);
                mGroupMemberUIDs.remove(j);
                mMembersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mGroupMembersRef.addChildEventListener(childEventListener);

        mMemberSelectEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CreateIssueActivity.this)
                        .setAdapter(mMembersAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mMemberSelectEditText.setText(mGroupMemberNames.get(which));
                                mAssignedToUID = mGroupMemberUIDs.get(which);
                            }
                        })
                        .setTitle("Select Member")
                        .show();
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
