package com.example.lucasgaleano.buttonexample.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lucasgaleano.buttonexample.R;
import com.example.lucasgaleano.buttonexample.database.Subject;
import com.example.lucasgaleano.buttonexample.dialog.SubjectDialogFragment;
import com.example.lucasgaleano.buttonexample.view.SubjectTreeModel;
import com.example.lucasgaleano.buttonexample.view.SubjectTreeView;
import com.example.lucasgaleano.buttonexample.view.SubjectView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private SubjectTreeView mSystemTreeView;
    private SubjectTreeModel treeModel;
    public static final String EXTRA_NAME_SUBJECT = "com.example.lucasgaleano.buttonexample.extra.NAME_SUBJECT";
    public static final String EXTRA_STATE_SUBJECT = "com.example.lucasgaleano.buttonexample.extra.STATE_SUBJECT";
    public static final String EXTRA_LEVEL_SUBJECT = "com.example.lucasgaleano.buttonexample.extra.LEVEL_SUBJECT";
    public static final String EXTRA_POS_SUBJECT = "com.example.lucasgaleano.buttonexample.extra.POS_SUBJECT";
    private Intent intentSubjectActivity;
    private MainActivity context;
    private List<Subject> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("color", String.valueOf(getResources().getInteger(R.integer.HABILITADA)));
        treeModel = ViewModelProviders.of(this).get(SubjectTreeModel.class);
        mSystemTreeView = findViewById(R.id.treeView);
        mSystemTreeView.setOnClicks(clicks);
        intentSubjectActivity = new Intent(this, SubjectActivity.class);

//        subjects = treeModel.getSubjectList();

//        for (SubjectView sub: subjects){
//            mSystemTreeView.addNode(sub.getText().toString(),sub.getLevel(),sub.getPosition(),sub.getState(),sub.getPredecessor());
//
//        }
//
        treeModel.getSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable final List<Subject> subjects) {
                for (Subject sub : subjects) {
                    mSystemTreeView.updateNode(sub.getName(),sub.getLevel(),sub.getPosition(),sub.getState(),treeModel.getPredecessor(sub.getName()));
                }
                mSystemTreeView.doInvalidate();
            }
        });
    }


    View.OnClickListener clicks = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SubjectView sub = (SubjectView) v;
            intentSubjectActivity.putExtra(EXTRA_NAME_SUBJECT, sub.getText().toString());
            intentSubjectActivity.putExtra(EXTRA_STATE_SUBJECT, sub.getState());
            Log.d(TAG,"click on subject: " + sub.getText().toString() + "\nlevel: "
                            + String.valueOf(sub.getLevel()) + "\nstate: "
                            + String.valueOf(sub.getState()) + "\nposition:"
                            + String.valueOf(sub.getPosition()));
            if(sub.getState() == getResources().getInteger(R.integer.HABILITADA)){
                DialogFragment dialog = new SubjectDialogFragment();
                Bundle args = new Bundle();
                args.putString("SubjectName",sub.getText().toString());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(),"Choose");
            }else{
                startActivity(intentSubjectActivity);
            }


        }
    };


}
