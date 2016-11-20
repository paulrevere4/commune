package xyz.communeapp.commune;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {

    Context context;
    String mGroupName;
    String mGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        context = this;

        Intent intent=this.getIntent();

        TextView group_name_textView = (TextView) findViewById(R.id.group_name_textView);

        TextView group_id_textView = (TextView) findViewById(R.id.group_id_textView);

        if(intent !=null){
            mGroupName = intent.getStringExtra("GROUP_NAME");
            mGroupID = intent.getStringExtra("GROUP_ID");
            group_name_textView.setText(mGroupName);
            group_id_textView.setText(mGroupID);
        }

        Button members_button = (Button) findViewById(R.id.members_button);
        Button tasks_button = (Button) findViewById(R.id.tasks_button);
        Button resources_button = (Button) findViewById(R.id.resources_button);

        members_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemberListActivity.class);
                intent.putExtra("GROUP_ID", mGroupID);
                intent.putExtra("GROUP_NAME", mGroupName);
                startActivity(intent);
            }
        });

        tasks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        resources_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }
}
