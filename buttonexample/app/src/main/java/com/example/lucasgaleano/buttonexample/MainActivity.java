package com.example.lucasgaleano.buttonexample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.lucasgaleano.buttonexample.database.Dao;
import com.example.lucasgaleano.buttonexample.database.Subject;
import com.example.lucasgaleano.buttonexample.view.SubjectTreeModel;
import com.example.lucasgaleano.buttonexample.view.SubjectTreeView;
import com.example.lucasgaleano.buttonexample.view.SubjectView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SubjectTreeView mSystemTreeView;
    private SubjectTreeModel treeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        treeModel = ViewModelProviders.of(this).get(SubjectTreeModel.class);
        mSystemTreeView = findViewById(R.id.treeView);
        fillTreeNode();


        treeModel.getSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable final List<Subject> subjects) {
                // Update the cached copy of the words in the adapter.
                for(Subject sub : subjects){

                    mSystemTreeView.updateNode(sub.getName(), sub.getState());
                    mSystemTreeView.doInvalidate();
                }
            }
        });


    }

    private void fillTreeNode() {

        List<Subject> subs = treeModel.getSubjects().getValue();
        if(subs==null)return;
        for(Subject sub:subs){
            mSystemTreeView.addNode(sub.getName(),sub.getLevel(),sub.getPosition(),sub.getState(),treeModel.getPredecessor(sub.getName()).getValue());
        }
        mSystemTreeView.setOnClicks(clicks);
    }

    View.OnClickListener clicks = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getApplication().getBaseContext();
            SubjectView subj = (SubjectView) v;
            CharSequence text = subj.getText().toString();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            treeModel.updateView(subj);
            mSystemTreeView.doInvalidate();
        }
    };


}
