package xyz.communeapp.commune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddGroupActivity extends AppCompatActivity {

    private EditText name;
    private EditText emails;

    private void createGroup(){
        Group newGroup = new Group(name.getText().toString(), FirebaseAuth.getInstance().getCurrentUser(), FirebaseDatabase.getInstance());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Button create = (Button) findViewById(R.id.addGroupButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();
            }
        });
        name = (EditText) findViewById(R.id.groupNameEditText);
        emails = (EditText) findViewById(R.id.groupUsersEditText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
