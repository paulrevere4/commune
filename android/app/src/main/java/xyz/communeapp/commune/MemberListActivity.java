package xyz.communeapp.commune;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberListActivity extends AppCompatActivity implements AddUserDialog.NoticeDialogListener {

    private ArrayList<String> members;
    private ArrayList<String> member_ids;
    private GroupListCustomAdapter adapter;
    private String mGroupID;
    private String mGroupName;
    private DatabaseReference mGroupRef;
    private AddUserDialog mAddUserDialog;

    private void addUserToGroup(final String UID){
        // Get user reference using uid
        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(UID).child("Name").getRef();

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mGroupRef.child(UID).setValue(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, final String email) {
        // User touched the dialog's positive button
        Log.e("asdf","add clicked "+ email);

        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Email").equalTo(email).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    // Find User Node
                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();

                    // Find user reference and add the new group
                    firstChild.getRef().child("Groups").child(mGroupID).setValue(mGroupName);

                    // Get the UID of the user
                    final String UID = firstChild.getKey();

                    addUserToGroup(UID);

                }else{
                    Log.e("test",email+" does not exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("test",databaseError.getDetails());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        mGroupID = getIntent().getStringExtra("GROUP_ID");
        mGroupName = getIntent().getStringExtra("GROUP_NAME");

        mGroupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID).child("Members").getRef();

        members = new ArrayList<>();

        member_ids = new ArrayList<>();

        adapter =  new GroupListCustomAdapter(this, members);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adsadf", "onChildAdded:" + dataSnapshot.getValue());
                members.add(dataSnapshot.getValue().toString());
                member_ids.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adf", "onChildChanged:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("adf", "onChildRemoved:" + dataSnapshot.getKey());
                int i = members.indexOf(dataSnapshot.getValue().toString());
                int j = member_ids.indexOf(dataSnapshot.getKey());
                members.remove(i);
                member_ids.remove(j);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adf", "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mGroupRef.addChildEventListener(childEventListener);

        ListView group_list = (ListView) findViewById(R.id.members_list);
        group_list.setAdapter(adapter);

        mAddUserDialog = new AddUserDialog();

        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String member = String.valueOf(adapterView.getItemAtPosition(i));
                String member_id = member_ids.get(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_member_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddUserDialog.show(getSupportFragmentManager(), "AddUserToGroupDialogFragment");
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
