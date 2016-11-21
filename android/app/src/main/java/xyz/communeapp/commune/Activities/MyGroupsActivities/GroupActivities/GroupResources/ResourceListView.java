package xyz.communeapp.commune.Activities.MyGroupsActivities.GroupActivities.GroupResources;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import xyz.communeapp.commune.Classes.Resource;
import xyz.communeapp.commune.Classes.ResourceCreator;
import xyz.communeapp.commune.Dialogs.AddResourceDialog;
import xyz.communeapp.commune.Dialogs.RemoveResourceDialog;
import xyz.communeapp.commune.ListAdapters.ResourceListCustomAdapter;
import xyz.communeapp.commune.R;

public class ResourceListView extends AppCompatActivity implements AddResourceDialog.NoticeDialogListener, RemoveResourceDialog.RemoveNoticeDialogListener {

    private String mGroupID;
    private DatabaseReference mGroupRef;
    private String mGroupName;
    private String mCurrentUserUID;
    private ArrayList<String> mResources;
    private ArrayList<String> mResourceIDs;
    private ArrayList<String> mResourceDetails;
    private ArrayList<String> mResourceNamesAndDetails;
    private ResourceListCustomAdapter mAdapter;
    private AddResourceDialog mAddResourceDialog;
    private RemoveResourceDialog mRemoveResourceDialog;
    private String mSelectedResourceID;

    @Override
    public void onRemoveDialogPositiveClick(DialogFragment dialog){
        mGroupRef.child(mSelectedResourceID).removeValue();
        dialog.dismiss();
        recreate();
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.RemoveNoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, final String name, final String details) {
        Resource newResource = new Resource(name,details);
        new ResourceCreator(mGroupRef,newResource).create();
        dialog.dismiss();
        recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_list_view);

        mGroupID = getIntent().getStringExtra("GROUP_ID");
        mGroupName = getIntent().getStringExtra("GROUP_NAME");
        mCurrentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference group_ref = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID).child("CreatorUid").getRef();

        mGroupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(mGroupID)
                .child("Resources").getRef();

        mResources = new ArrayList<>();
        mResourceIDs = new ArrayList<>();
        mResourceDetails = new ArrayList<>();
        mResourceNamesAndDetails = new ArrayList<>();

        mAdapter = new ResourceListCustomAdapter(this, mResourceNamesAndDetails);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if(dataSnapshot.getKey() != null){
                    mResourceIDs.add(dataSnapshot.getKey());
                    if(dataSnapshot.child("Name").getValue() != null){
                        mResources.add(dataSnapshot.child("Name").getValue().toString());
                        String name = dataSnapshot.child("Name").getValue().toString();
                        if(dataSnapshot.child("Details").getValue() != null){
                            mResourceDetails.add(dataSnapshot.child("Details").getValue().toString());
                            String details = dataSnapshot.child("Details").getValue().toString();
                            mResourceNamesAndDetails.add(name+"/"+details);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                recreate();
//                if(dataSnapshot.getKey() != null){
//                    mResourceIDs.remove(mResourceIDs.indexOf(dataSnapshot.getKey()));
//                }
//                if(dataSnapshot.child("Name").getValue() != null){
//
//                }
//
//                if(dataSnapshot.getKey() != null){
//                    mResourceIDs.remove(mResourceIDs.indexOf(dataSnapshot.getKey()));
//                    if(dataSnapshot.child("Name").getValue() != null){
//                        mResources.remove(mResources.indexOf(dataSnapshot.child("Name").getValue().toString()));
//                        String name = dataSnapshot.child("Name").getValue().toString();
//                        if(dataSnapshot.child("Details").getValue() != null){
//                            mResourceDetails.remove(mResourceDetails.indexOf(dataSnapshot.child("Details").getValue().toString()));
//                            String details = dataSnapshot.child("Details").getValue().toString();
//                            mResourceNamesAndDetails.remove(mResourceNamesAndDetails.indexOf(name+"/"+details));
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mGroupRef.addChildEventListener(childEventListener);

        ListView resource_list = (ListView) findViewById(R.id.resources_list);
        resource_list.setAdapter(mAdapter);

        mAddResourceDialog = new AddResourceDialog();
        mRemoveResourceDialog = new RemoveResourceDialog();

        resource_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedResourceID = mResourceIDs.get(i);
                mRemoveResourceDialog.show(getSupportFragmentManager(),"RemoveResourceDialogFragment");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_resource_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddResourceDialog.show(getSupportFragmentManager(),
                        "AddResourceDialogFragment");
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
