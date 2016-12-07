package xyz.communeapp.commune.MainClasses;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Adds an user to the database
 */
public class AddUserToDatabase {

    private User user;
    private DatabaseReference reference;

    /**
     * Constructor
     *
     * @param user user to be written to database
     */
    public AddUserToDatabase(User user) {
        this.user = user;
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    /**
     * Writes the user to the database
     */
    public void WriteUserToDatabase() {
        // Create new user node
        DatabaseReference ref = reference.child(user.getUid()).getRef();
        // Write user name under new user node
        ref.child("Name").setValue(user.getName());
        // Write user email under new user node
        ref.child("Email").setValue(user.getEmail());
    }
}
