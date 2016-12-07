package xyz.communeapp.commune.MainClasses;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds information about a group in the app
 */
public class Group {

    private String name;
    private FirebaseUser creator;
    private DatabaseReference databaseRef;
    private FirebaseDatabase database;
    private String groupUID;

    /**
     * Constructor for creating a new group.
     *
     * @param name     Name of the group
     * @param users    Users to be added to the group, string of emails delimited by ","
     * @param creator  User who created the group
     * @param database Database where the group will be held
     */
    public Group(String name, String users, FirebaseUser creator, final FirebaseDatabase database) {
        this.name = name;
        this.creator = creator;
        this.database = database;

        createGroupInDatabase();

        // Split the string into separate email addresses
        final String[] user_emails_array = users.split(",");

        final List<String> user_emails_array_list = new ArrayList<>(Arrays.asList
                (user_emails_array));

        user_emails_array_list.add(creator.getEmail());

        addGroupToUsers(user_emails_array_list, this.name, this.databaseRef, this.groupUID);
    }

    /**
     * Creates a group in Firebase
     */
    private void createGroupInDatabase() {
        // Create a new group in the database
        this.databaseRef = database.getReference().child("Groups").push();
        this.groupUID = databaseRef.getKey();
        // Set the group name
        databaseRef.child("Name").setValue(name);
        // Set the group creator Uid
        databaseRef.child("CreatorUid").setValue(creator.getUid());
    }

    /**
     * Adds a user to the group
     *
     * @param group_ref DB reference to the group being added to
     * @param UID       ID of the user being added
     */
    private void addUserToGroup(final DatabaseReference group_ref, final String UID) {
        // Get user reference using uid
        DatabaseReference user_ref = database.getReference().child("Users").child(UID).child
                ("Name").getRef();

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                group_ref.child("Members").child(UID).setValue(dataSnapshot.getValue());
                float value = 0;
                group_ref.child("MonetaryContributions").child(UID).setValue(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Links a group to the members in it so they will receive notifications
     *
     * @param emails    List of strings of the emails of the users
     * @param groupName Name of the group
     * @param group_ref Reference to the group
     * @param groupUID  ID of the group
     */
    private void addGroupToUsers(List<String> emails, final String groupName, final
    DatabaseReference group_ref, final String groupUID) {
        for (final String user : emails) {
            database.getReference().child("Users").orderByChild("Email").equalTo(user)
                    .limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        // Find User Node
                        DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();

                        Log.e("sdfsad", firstChild.getRef().toString());

                        // Find user reference and add the new group
                        firstChild.getRef().child("Groups").child(groupUID).setValue(groupName);

                        // Get the UID of the user
                        final String UID = firstChild.getKey();

                        addUserToGroup(group_ref, UID);

                    } else {
                        Log.e("test", user + " does not exist");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("test", databaseError.getDetails());
                }
            });
        }
    }
}
