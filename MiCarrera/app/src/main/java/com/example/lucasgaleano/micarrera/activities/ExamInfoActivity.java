package com.example.lucasgaleano.micarrera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.classes.Calendario;
import com.example.lucasgaleano.micarrera.database.Exam;
import com.example.lucasgaleano.micarrera.database.Repository;

public class ExamInfoActivity extends AppCompatActivity {

    public TextView titleTxt;
    private TextView dateTxt;
    private TextView descriptionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exam);

        Intent intent = getIntent();
        int id = intent.getIntExtra(SubjectActivity.EXTRA_ID,-1);

        Repository repo = new Repository(getApplication());
        Exam exam = repo.getExamById(id);

        updateData(exam);




    }

    private void updateData(Exam exam) {

        titleTxt = findViewById(R.id.tittle_txt);
        dateTxt = findViewById(R.id.date_txt);
        descriptionTxt = findViewById(R.id.description_txt);

        titleTxt.setText( exam.getSubject().concat( Exam.get(exam.getType()) ));
        dateTxt.setText(Calendario.formatDate(exam.getDate()));
        descriptionTxt.setText(exam.getDescription());

    }
}


