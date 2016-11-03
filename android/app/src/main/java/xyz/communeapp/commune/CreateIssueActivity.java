package xyz.communeapp.commune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateIssueActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText group;

    private void createIssue(){
        Issue newIssue = new Issue(name.getText().toString(), description.getText().toString(), group.getText().toString(), FirebaseAuth.getInstance().getCurrentUser(), FirebaseDatabase.getInstance());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);
        Button create = (Button) findViewById(R.id.createIssueButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIssue();
            }
        });
        name = (EditText) findViewById(R.id.issueNameEditText);
        description = (EditText) findViewById(R.id.issueDescriptionEditText);
        group = (EditText) findViewById(R.id.issueGroupEditText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
