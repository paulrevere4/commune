package xyz.communeapp.commune.Activities.MyGroupsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities.GroupActivity;
import xyz.communeapp.commune.ListAdapters.GroupListCustomAdapter;
import xyz.communeapp.commune.R;

public class GroupsListViewActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference groups_ref;
    private ArrayList<String> mobileArray;
    private GroupListCustomAdapter adapter;

    private void createGroup() {
        Intent intent = new Intent(this, AddGroupActivity.class);
        startActivity(intent);
    }

    private void addToGroupsList(String groupName) {
        mobileArray.add(groupName);
    }

    private void startGroupActivity(String name, String group_id) {
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("GROUP_NAME", name);
        intent.putExtra("GROUP_ID", group_id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        groups_ref = database.getReference().child("Users").child(user.getUid()).child("Groups")
                .getRef();

        mobileArray = new ArrayList<>();
        final ArrayList<String> group_IDs = new ArrayList<>();

        adapter = new GroupListCustomAdapter(this, mobileArray);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adsadf", "onChildAdded:" + dataSnapshot.getValue());
                mobileArray.add(dataSnapshot.getValue().toString());
                group_IDs.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("adf", "onChildChanged:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("adf", "onChildRemoved:" + dataSnapshot.getKey());
                int i = mobileArray.indexOf(dataSnapshot.getValue().toString());
                int j = group_IDs.indexOf(dataSnapshot.getKey());
                mobileArray.remove(i);
                group_IDs.remove(j);
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

        groups_ref.addChildEventListener(childEventListener);

        Log.e("tst", groups_ref.toString());


        ListView group_list = (ListView) findViewById(R.id.mobile_list);
        group_list.setAdapter(adapter);

        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String group = String.valueOf(adapterView.getItemAtPosition(i));
                String group_id = group_IDs.get(i);
                startGroupActivity(group, group_id);
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
