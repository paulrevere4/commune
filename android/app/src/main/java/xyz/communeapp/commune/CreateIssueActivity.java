package xyz.communeapp.commune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateIssueActivity extends AppCompatActivity {
    private EditText name;  // Issue name
    private EditText description; //Issue description
    private EditText group; // Issue group

    // Creates a issue instance
    private void createIssue(){
        Issue newIssue = new Issue(name.getText().toString(), description.getText().toString(), group.getText().toString(), FirebaseAuth.getInstance().getCurrentUser(), FirebaseDatabase.getInstance());
        finish(); // Quit the activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);

        // Get UI elements
        Button create = (Button) findViewById(R.id.createIssueButton);
        name = (EditText) findViewById(R.id.issueNameEditText);
        description = (EditText) findViewById(R.id.issueDescriptionEditText);
        group = (EditText) findViewById(R.id.issueGroupEditText);

        // Set on-click listener to button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIssue();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
