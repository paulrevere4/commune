package xyz.communeapp.commune.Activities.MyGroupsActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import xyz.communeapp.commune.Classes.Group;
import xyz.communeapp.commune.R;

public class AddGroupActivity extends AppCompatActivity {

    private EditText name;  // Group name
    private EditText emails; // Group members emails

    // Creates a group instance
    public void createGroup() {
        Group newGroup = new Group(name.getText().toString(), emails.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser(), FirebaseDatabase.getInstance());
        finish(); // Quit the activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // Get UI elements
        Button create = (Button) findViewById(R.id.addGroupButton);
        name = (EditText) findViewById(R.id.issueNameEditText);
        emails = (EditText) findViewById(R.id.groupUsersEditText);

        // Set on-click listener to button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
