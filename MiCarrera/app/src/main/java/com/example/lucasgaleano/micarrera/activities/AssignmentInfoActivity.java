package com.example.lucasgaleano.micarrera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.classes.Calendario;
import com.example.lucasgaleano.micarrera.database.Assignment;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.dialog.EditDialogFragment;

public class AssignmentInfoActivity extends AppCompatActivity {


    private TextView titleTxt;
    private TextView dateTxt;
    private TextView descriptionTxt;
    public Assignment assignment;
    public Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        Intent intent = getIntent();
        int id = intent.getIntExtra(SubjectActivity.EXTRA_ID, -1);

        repo = new Repository(getApplication());
        assignment = repo.getAssigmentById(id);

        titleTxt = findViewById(R.id.title);
        dateTxt = findViewById(R.id.date);
        descriptionTxt = findViewById(R.id.description);

        updateData(assignment);


    }

    private void updateData(Assignment assignment) {

        titleTxt.setText(assignment.getTitle());
        dateTxt.setText(Calendario.formatDate(assignment.getDate()));
        descriptionTxt.setText(assignment.getDescription());

        titleTxt.setOnClickListener(clicks);
        dateTxt.setOnClickListener(clicks);
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
}