package xyz.communeapp.commune.MainClasses;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Holds information about a user in the app
 */
public class User {

    private String name;    // User name
    private String uid;     // Unique user id
    private String email;   // User email
    private DatabaseReference userRef; // User database reference

    /**
     * Constructor for creating a new User
     *
     * @param user
     */
    public User(FirebaseUser user) {
        this.name = user.getDisplayName();
        this.uid = user.getUid();
        this.email = user.getEmail();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid
                ()).getRef();
    }

    /**
     * Getter for user name
     *
     * @return user name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for user name
     *
     * @param name name to be set for user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for user email
     *
     * @return user email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for user email
     *
     * @param email email to be set for user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for user UID
     *
     * @return user UID
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * Getter for user database reference
     *
     * @return database reference to user
     */
    public DatabaseReference getUserRef() {
        return userRef;
    }

    /**
     * Setter for user database reference
     *
     * @param userRef database reference to bet set for user
     */
    public void setUserRef(DatabaseReference userRef) {
        this.userRef = userRef;
    }
}
