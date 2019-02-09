package com.example.lucasgaleano.micarrera.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.classes.Calendario;
import com.example.lucasgaleano.micarrera.database.Assignment;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.dialog.EditDialogFragment;

import java.util.Calendar;

public class AssignmentInfoActivity extends AppCompatActivity{

    private TextView titleTxt;
    private TextView dateTxt;
    private TextView descriptionTxt;
    public Assignment assignment;
    public Repository repo;
    public Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        Intent intent = getIntent();
        int id = intent.getIntExtra(SubjectActivity.EXTRA_ID, -1);

        repo = new Repository(getApplication());
        if(id == -1) {
            assignment = new Assignment(Calendar.getInstance(), "prueba", "", "blah blah blah", "", "", 0);
        }
        else {
            assignment = repo.getAssigmentById(id);
        }
        titleTxt = findViewById(R.id.title);
        dateTxt = findViewById(R.id.date);
        descriptionTxt = findViewById(R.id.description);

        updateData(assignment);

        final Calendar myCalendar = Calendar.getInstance();
    }

    private void updateData(Assignment assignment) {

        titleTxt.setText(assignment.getTitle());
        dateTxt.setText(Calendario.formatDate(assignment.getDate()));
        descriptionTxt.setText(assignment.getDescription());

        titleTxt.setOnClickListener(clicks);
        dateTxt.setOnClickListener(datePicker);
        descriptionTxt.setOnClickListener(clicks);
    }

    View.OnClickListener clicks = new View.OnClickListener() {
        //@SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            TextView texto = (TextView) v;
            DialogFragment dialog = new EditDialogFragment();
            Bundle args = new Bundle();
            args.putString("EXTRA_TITLE", texto.getResources().getResourceEntryName(texto.getId()));
            args.putString("EXTRA_CONTENT", texto.getText().toString());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"Choose");
        }
    };

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar aux = Calendar.getInstance();
            aux.set(Calendar.YEAR,year);
            aux.set(Calendar.MONTH,monthOfYear);
            aux.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            assignment.setDate(aux);
            repo.update(assignment);
            recreate();
        }

    };


    View.OnClickListener datePicker = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {

            new DatePickerDialog(AssignmentInfoActivity.this, date, assignment.getDate().get(Calendar.YEAR),
                    assignment.getDate().get(Calendar.MONTH),
                    assignment.getDate().get(Calendar.DAY_OF_MONTH)).show();
        }
    };

}