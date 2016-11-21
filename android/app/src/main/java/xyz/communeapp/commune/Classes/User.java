package xyz.communeapp.commune.Classes;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String name;    // user name
    private String uid;     // unique user id
    private DatabaseReference databaseRef; // database reference to user
    private String email;   // user email

    public User() {

    }

    public User(FirebaseUser user, FirebaseDatabase database) {
        this.name = user.getDisplayName();
        this.uid = user.getUid();
        this.email = user.getEmail();

        this.databaseRef = database.getReference().child("Users").child(user.getUid()); //Create
        // a new user user Users with Uid as key
        databaseRef.child("Name").setValue(this.name);  //Set the new user's name as a child with
        // key name
        databaseRef.child("Email").setValue(this.email); //Set the new user's email as a child
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

    // Set the database reference to the user
    public void setDatabaseRef(DatabaseReference ref) {
        this.databaseRef = ref;
    }
}
