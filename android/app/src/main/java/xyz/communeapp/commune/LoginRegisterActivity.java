package xyz.communeapp.commune;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginRegisterActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private User mUser;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mFirebaseUser;

    // Start the main activity of the app
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("User", mUser.getUid()); // Send user's name to the main activity
        startActivity(intent);
        finish(); // close the current activity
    }

    private void getUserInformation() {
        // If already logged in, get user information from database
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser = new User(mFirebaseUser, mDatabase); // Create a user class instance
    }

    // Callback function for the login activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // If login succeeded, get database and user information
                getUserInformation();
                startMainActivity();
            } else {
                // Close the activity if login was cancelled or failed
                finish();
            }
        }
    }

    @Override
    public void onResume() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // If the user is not logged in, start the login activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER,
                                    AuthUI.FACEBOOK_PROVIDER)
                            .build(),
                    RC_SIGN_IN);
        }else{
            // If already logged in, get user information from database
            getUserInformation();
            startMainActivity(); // Move onto the main activity after getting user information
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
