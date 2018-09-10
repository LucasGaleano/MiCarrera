package com.example.lucasgaleano.micarrera.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.BoringLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.database.Subject;
import com.example.lucasgaleano.micarrera.view.SubjectTreeModel;
import com.example.lucasgaleano.micarrera.view.SubjectView;
import com.example.lucasgaleano.micarrera.R;

public class SubjectActivity extends Activity {


    private TextView mtv;
    private Switch mSwitch;
    private String subjectName;
    private Repository repo;
    private Subject sub;
    private int subjectState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        //treeModel = ViewModelProviders.of(this).get(SubjectTreeModel.class);
        repo = new Repository(getApplication());
        Intent intent = getIntent();
        subjectState = intent.getIntExtra(TreeActivity.EXTRA_STATE_SUBJECT,-1);
        subjectName = intent.getStringExtra(TreeActivity.EXTRA_NAME_SUBJECT);
        sub = new Subject(subjectName,
                subjectState,0,0);



        mtv = findViewById(R.id.textView2);
        mtv.setText(subjectName);
        mSwitch = findViewById(R.id.switch1);
        boolean b = subjectState==1;
        mSwitch.setChecked(b);
        mSwitch.setText("Dejar de cursar");

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                    sub.setState(getResources().getInteger(R.integer.HABILITADA));
                repo.updateSubject(sub);

            }
        });

    }
}
