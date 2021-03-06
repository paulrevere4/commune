package xyz.communeapp.commune.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import xyz.communeapp.commune.Activities.IssueActivity.MyIssueListViewActivity;
import xyz.communeapp.commune.Activities.MyGroupsActivities.GroupsListViewActivity;
import xyz.communeapp.commune.MainClasses.AddUserToDatabase;
import xyz.communeapp.commune.MainClasses.User;
import xyz.communeapp.commune.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireDatabase;
    private User mUser;

    /**
     * Creates an intent for GroupListActivity and starts the activity
     */
    private void startGroupsListViewActivity() {
        Intent intent = new Intent(this, GroupsListViewActivity.class);
        startActivity(intent);
    }

    /**
     * Creates an intent for MyIssueListViewActivity and starts the activity
     */
    private void startIssueListViewActivity() {
        Intent intent = new Intent(this, MyIssueListViewActivity.class);
        startActivity(intent);
    }

    /**
     * Function is called when the activity is started to set up the activity
     * UI elements are set up and user is written to database if first time signing in
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the UI elements
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView user_name = (TextView) findViewById(R.id.user_name_textview);
        TextView user_email = (TextView) findViewById(R.id.user_email_textview);
        Button my_issues = (Button) findViewById(R.id.my_issues);
        Button my_groups = (Button) findViewById(R.id.my_groups);

        // Set On-Click-Listeners for buttons
        my_issues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIssueListViewActivity();
            }
        });
        my_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGroupsListViewActivity();
            }
        });

        // If the user is not logged in, start the login activity
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // If the user is not logged in, start the login activity
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else {
            // If the user is logged in, get user instance from Firebase
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            mFireDatabase = FirebaseDatabase.getInstance();
            // Create user object
            mUser = new User(mFirebaseUser);
            // Write user to database
            new AddUserToDatabase(mUser).WriteUserToDatabase();
            // Set UI elements to user information
            user_name.setText(mUser.getName());
            user_email.setText(mFirebaseUser.getEmail());
            if (mFirebaseUser.getDisplayName() == null) {
                logOut();
                Intent intent = new Intent(this, LoginRegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * Callback function menu button click
     *
     * @param menu menu object
     * @return boolean indicating success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Logs a user out
     */
    private void logOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    /**
     * Callback function for items selected in menu
     *
     * @param item menu item
     * @return boolean indicating success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logOut();   //Logs the user out
            // Go back to the login/register activity and kill this activity
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Intent intent = new Intent(this, LoginRegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A callback function that is triggered when the activity is active
     */
    @Override
    public void onResume() {
        // If the user is not logged in, close the main activity
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            finish();
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
}
