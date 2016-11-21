package xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities.GroupIssues.GroupIssuesActivity;


import xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities.GroupMembers
        .MemberListActivity;
import xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities.GroupResources
        .ResourceListView;
import xyz.communeapp.commune.R;

public class GroupActivity extends AppCompatActivity {

    private Context context;
    private String mGroupName;
    private String mGroupID;
    private String mUserUID;
    private String mGroupCreatorUID;
    private DatabaseReference mGroupRef;

    private void leaveGroup() {
        mGroupRef.child("Members").child(mUserUID).removeValue();
        mGroupRef.child("MonetaryContributions").child(mUserUID).removeValue();
        FirebaseDatabase.getInstance().getReference().child("Users").child(mUserUID).child
                ("Groups").child(mGroupID).removeValue();
        finish();
    }

    private void deleteGroup(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(mUserUID).child("Groups").child(mGroupID).removeValue();
        mGroupRef.removeValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        context = this;

        Intent intent = this.getIntent();

        TextView group_name_textView = (TextView) findViewById(R.id.group_name_textView);

        TextView group_id_textView = (TextView) findViewById(R.id.group_id_textView);

        if (intent != null) {
            mGroupName = intent.getStringExtra("GROUP_NAME");
            mGroupID = intent.getStringExtra("GROUP_ID");
            group_name_textView.setText(mGroupName);
            group_id_textView.setText(mGroupID);
        }

        mUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mGroupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID)
                .getRef();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mGroupCreatorUID = dataSnapshot.child("CreatorUid").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mGroupRef.addValueEventListener(listener);

        Button members_button = (Button) findViewById(R.id.members_button);
        Button issues_button = (Button) findViewById(R.id.issues_button);
        Button resources_button = (Button) findViewById(R.id.resources_button);
        Button leave_group_button = (Button) findViewById(R.id.leave_group_button);
        Button delete_group_button = (Button) findViewById(R.id.delete_group_button);

        members_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemberListActivity.class);
                intent.putExtra("GROUP_ID", mGroupID);
                intent.putExtra("GROUP_NAME", mGroupName);
                startActivity(intent);
            }
        });

        issues_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GroupIssuesActivity.class);
                intent.putExtra("GROUP_ID", mGroupID);
                intent.putExtra("GROUP_NAME", mGroupName);
                startActivity(intent);
            }
        });

        resources_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResourceListView.class);
                intent.putExtra("GROUP_ID", mGroupID);
                intent.putExtra("GROUP_NAME", mGroupName);
                startActivity(intent);
            }
        });

        leave_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mUserUID.equals(mGroupCreatorUID)) {
                    new AlertDialog.Builder(context).setTitle("Leave Group").setMessage("Creator " +
                            "" + "of group cannot leave!").setIcon(android.R.drawable
                            .ic_dialog_alert).show();
                } else {
                    new AlertDialog.Builder(context).setTitle("Leave Group").setMessage("Are you " +
                            "" + "sure you want to leave this group?").setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            leaveGroup();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }
        });

        delete_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserUID.equals(mGroupCreatorUID)) {
                    new AlertDialog.Builder(context).setTitle("Delete Group").setMessage("Are you " +
                            "" + "sure you want to delete this group?").setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteGroup();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
                } else {
                    new AlertDialog.Builder(context).setTitle("Delete Group").setMessage("You do not have permission to delete group").setIcon(android.R.drawable
                            .ic_dialog_alert).show();
                }
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
