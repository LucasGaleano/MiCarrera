package com.example.lucasgaleano.micarrera.activities;


import android.arch.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.database.Exam;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.database.Subject;
import com.example.lucasgaleano.micarrera.R;

import java.util.List;

public class SubjectActivity extends AppCompatActivity {


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

        repo.getAllExam().observe(this, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable final List<Exam> exams) {
                for (Exam exam : exams) {
                    Log.d("examen nombre: ", exam.getSubject());
                    Log.d("examen nota: ", String.valueOf(exam.getScore()));
                    Log.d("examen fecha: ", exam.getDate().toString());
                }
            }
        });

    }
}
