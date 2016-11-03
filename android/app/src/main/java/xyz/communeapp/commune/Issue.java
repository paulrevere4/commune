package xyz.communeapp.commune;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rabi on 11/3/16.
 */

public class Issue {

    private String name;    // Issue name
    private String description; // Issue description
    private FirebaseUser creator; // Issue creator
    private DatabaseReference databaseRef; // Database reference to the issue
    private String issueId; // Unique issue ID created randomly by Firebase

    public Issue() {

    }

    public Issue(String name, String description, String group, FirebaseUser creator, FirebaseDatabase database) {
        this.name = name;
        this.description = description;
        this.creator = creator;

        this.databaseRef = database.getReference().child("Issues").push();  //Creator new issue
        this.issueId = databaseRef.getKey();                                //Get the ID Firebase associated with the new issue
        databaseRef.child("Name").setValue(name);                           //Set issue name
        databaseRef.child("CreatorUid").setValue(creator.getUid());         //Set issue creator Uid
        databaseRef.child("Description").setValue(description);             //Set issue description
        databaseRef.child("Group").setValue(group);                         //Set issue group
    }

}
