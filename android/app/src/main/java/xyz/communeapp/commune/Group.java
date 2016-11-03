package xyz.communeapp.commune;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rabi on 11/2/16.
 */

public class Group {
    private String name;
    private FirebaseUser creator;
    private DatabaseReference databaseRef;

    public Group() {

    }

    public Group(String name, FirebaseUser creator, FirebaseDatabase database) {
        this.name = name;
        this.creator = creator;
        this.databaseRef = database.getReference().child("Groups").push();
        databaseRef.child("Name").setValue(name);
        databaseRef.child("CreatorUid").setValue(creator.getUid());
        databaseRef.child("Members").child(creator.getUid()).setValue(creator.getDisplayName());

        database.getReference().child("Users").child(creator.getUid()).child("Groups").push().child("Name").setValue(name);
    }
}
