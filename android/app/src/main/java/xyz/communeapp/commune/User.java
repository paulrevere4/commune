package xyz.communeapp.commune;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rabi on 10/27/16.
 */

public class User {
    private String name;
    private String uid;
    private DatabaseReference databaseRef;

    public User() {

    }

    public User(FirebaseUser user, FirebaseDatabase database) {
        this.name = user.getDisplayName();
        this.uid = user.getUid();
        this.databaseRef = database.getReference().child("Users").child(uid);
        databaseRef.child("Name").setValue(this.name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return this.uid;
    }

    public DatabaseReference getDatabaseRef() {
        return this.databaseRef;
    }

    public void setDatabaseRef(DatabaseReference ref) {
        this.databaseRef = ref;
    }
}
