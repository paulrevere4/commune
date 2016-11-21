package xyz.communeapp.commune.Activities.IssueActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import xyz.communeapp.commune.ListAdapters.GroupListCustomAdapter;
import xyz.communeapp.commune.R;

public class MyIssueListViewActivity extends AppCompatActivity {

    private FirebaseUser mUser;
    private ArrayList<String> issueGroupIDs;
    private DatabaseReference mUserIssuesRef;
    private GroupListCustomAdapter mAdapter;
    private ArrayList<String> issueNames;
    private ArrayList<String> issueIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issue_list_view);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserIssuesRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser
                .getUid()).child("Issues");

        issueNames = new ArrayList<>();
        issueIDs = new ArrayList<>();
        issueGroupIDs = new ArrayList<>();

        mAdapter = new GroupListCustomAdapter(this, issueNames);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.child("Name").getValue() != null) {
                    issueNames.add(dataSnapshot.child("Name").getValue().toString());
                    mAdapter.notifyDataSetChanged();
                }
                if (dataSnapshot.getKey() != null) {
                    issueIDs.add(dataSnapshot.getKey());
                }
                if (dataSnapshot.child("GroupID").getValue() != null) {
                    issueGroupIDs.add(dataSnapshot.child("GroupID").getValue().toString());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Name").getValue() != null) {
                    int i = issueNames.indexOf(dataSnapshot.child("Name").getValue().toString());
                    issueNames.remove(i);
                    mAdapter.notifyDataSetChanged();
                }
                if (dataSnapshot.getKey() != null) {
                    int j = issueIDs.indexOf(dataSnapshot.getKey());
                    issueIDs.remove(j);
                }
                if (dataSnapshot.child("GroupID").getValue() != null) {
                    int k = issueGroupIDs.indexOf(dataSnapshot.child("GroupID").getValue()
                            .toString());
                    issueGroupIDs.remove(k);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mUserIssuesRef.addChildEventListener(childEventListener);

        ListView myIssuesList = (ListView) findViewById(R.id.my_issues_listView);
        myIssuesList.setAdapter(mAdapter);

        myIssuesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyIssueListViewActivity.this, IssueActivity.class);
                intent.putExtra("ISSUE_ID", issueIDs.get(i));
                intent.putExtra("GROUP_ID", issueGroupIDs.get(i));
                startActivity(intent);
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
        return (super.onOptionsItemSelected(item));
    }
}
