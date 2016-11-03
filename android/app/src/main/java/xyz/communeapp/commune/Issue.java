package xyz.communeapp.commune;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rabi on 11/3/16.
 */

public class Issue {
    private String name;
    private String description;
    private FirebaseUser creator;
    private DatabaseReference databaseRef;
    private String issueId;

    public Issue() {

    }

    public Issue(String name, String description, String group, FirebaseUser creator, FirebaseDatabase database) {
        this.name = name;
        this.description = description;
        this.creator = creator;

        this.databaseRef = database.getReference().child("Issues").push();
        this.issueId = databaseRef.getKey();
        databaseRef.child("Name").setValue(name);
        databaseRef.child("CreatorUid").setValue(creator.getUid());
        databaseRef.child("Description").setValue(description);
        databaseRef.child("Group").setValue(group);
    }

}
