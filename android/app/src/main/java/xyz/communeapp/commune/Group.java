package xyz.communeapp.commune;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rabi on 11/2/16.
 */

public class Group {

    private String name;    // Group name
    private FirebaseUser creator; // Group creator
    private DatabaseReference databaseRef; // Reference to the group in the database

    public Group() {

    }

    public Group(String name, FirebaseUser creator, FirebaseDatabase database) {
        this.name = name;
        this.creator = creator;

        this.databaseRef = database.getReference().child("Groups").push(); // Create a new group in the databes

        databaseRef.child("Name").setValue(name);   // Set the group name
        databaseRef.child("CreatorUid").setValue(creator.getUid()); // Set the group creator Uid
        databaseRef.child("Members").child(creator.getUid()).setValue(creator.getDisplayName()); // Set the creator as a member of the group

        database.getReference().child("Users").child(creator.getUid()).child("Groups").push().child("Name").setValue(name); // Add the group under the creators groups
    }
}
