package com.example.lucasgaleano.micarrera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.classes.Calendario;
import com.example.lucasgaleano.micarrera.database.Assignment;
import com.example.lucasgaleano.micarrera.database.Repository;

public class AssignmentInfoActivity extends AppCompatActivity {


    private TextView titleTxt;
    private TextView dateTxt;
    private TextView descriptionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        Intent intent = getIntent();
        int id = intent.getIntExtra(SubjectActivity.EXTRA_ID, -1);

        Repository repo = new Repository(getApplication());
        Assignment assignment = repo.getAssigmentById(id);

        updateData(assignment);


    }

    private void updateData(Assignment assignment) {

        titleTxt = findViewById(R.id.tittle_txt);
        dateTxt = findViewById(R.id.date_txt);
        descriptionTxt = findViewById(R.id.description_txt);

        titleTxt.setText(assignment.getTitle());
        dateTxt.setText(Calendario.formatDate(assignment.getDate()));
        descriptionTxt.setText(assignment.getDescription());

    }
}