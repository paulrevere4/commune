package xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities.GroupIssues;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;

import xyz.communeapp.commune.Activities.IssueActivity.IssueActivity;
import xyz.communeapp.commune.ListAdapters.GroupListCustomAdapter;
import xyz.communeapp.commune.R;

public class GroupIssuesActivity extends AppCompatActivity {

    private ArrayList<String> issues;
    private ArrayList<String> issue_ids;
    private GroupListCustomAdapter adapter;
    private String mGroupID;
    private DatabaseReference mGroupRef;
    private String mSelectedIssueUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_issues_acitivity);

        mGroupID = getIntent().getStringExtra("GROUP_ID");

        mGroupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID)
                .child("Issues").getRef();
        issues = new ArrayList<>();
        issue_ids = new ArrayList<>();

        adapter = new GroupListCustomAdapter(this, issues);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adsadf", "onChildAdded:" + dataSnapshot.child("Name").getValue().toString());
                issues.add(dataSnapshot.child("Name").getValue().toString());
                issue_ids.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adf", "onChildChanged:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("adf", "onChildRemoved:" + dataSnapshot.getKey());
                int i = issues.indexOf(dataSnapshot.child("Name").getValue().toString());
                int j = issue_ids.indexOf(dataSnapshot.getKey());
                issues.remove(i);
                issue_ids.remove(j);
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

        ListView issue_list = (ListView) findViewById(R.id.issues_list);
        issue_list.setAdapter(adapter);

        issue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupIssuesActivity.this, IssueActivity.class);
                intent.putExtra("ISSUE_ID", issue_ids.get(i));
                intent.putExtra("GROUP_ID", mGroupID);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_issue_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupIssuesActivity.this, CreateIssueActivity.class);
                intent.putExtra("GROUP_ID", mGroupID);
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
