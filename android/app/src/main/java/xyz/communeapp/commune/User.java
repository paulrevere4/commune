package xyz.communeapp.commune;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rabi on 10/27/16.
 */

public class User {
    private String name;    // user name
    private String uid;     // unique user id
    private DatabaseReference databaseRef; // database reference to user

    public User() {

    }

    public User(FirebaseUser user, FirebaseDatabase database) {
        this.name = user.getDisplayName();
        this.uid = user.getUid();

        this.databaseRef = database.getReference().child("Users").child(uid); //Create a new user uner Users with Uid as key
        databaseRef.child("Name").setValue(this.name);  //Set the new user's name as a child with key name
    }

    // Return the name of the user
    public String getName() {
        return this.name;
    }
    // Set the name of the user
    public void setName(String name) {
        this.name = name;
    }
    // Get the uid of the user
    public String getUid() {
        return this.uid;
    }
    // Get the database reference to the user
    public DatabaseReference getDatabaseRef() {
        return this.databaseRef;
    }
    // Set the database reference to the uer
    public void setDatabaseRef(DatabaseReference ref) {
        this.databaseRef = ref;
    }
}
