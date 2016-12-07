package xyz.communeapp.commune.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRegisterActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100; // Unique code to identify a sign in request

    /**
     * Creates an intent to start the main activity and starts intent
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent); // start the activity
        finish(); // close the current activity
    }

    /**
     * A callback function that listens for results from an activity
     *
     * @param requestCode Unique code that identifies the type of request
     * @param resultCode  Unique result code that identifies the type of result
     * @param data        An intent that contains the data of the results
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // If login succeeded, start the main activity
                startMainActivity();
            } else {
                // Close the activity if login was cancelled or failed
                finish();
            }
        }
    }

    /**
     * A callback function that is triggered when the activity is active
     */
    @Override
    public void onResume() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // If the user is not logged in, start the login activity
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setProviders
                    (AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER, AuthUI.FACEBOOK_PROVIDER)
                    .build(), RC_SIGN_IN);
        } else {
            // Move onto the main activity
            startMainActivity();
        }
        super.onResume();
    }

    /**
     * Callback function for back button press that closes the activity
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Function is called when the activity is started to set up the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
