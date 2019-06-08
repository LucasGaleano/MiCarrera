package com.example.pyrca.micarrera.activities;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.classes.Calendario;
import com.example.pyrca.micarrera.database.Assignment;
import com.example.pyrca.micarrera.database.Repository;
import com.example.pyrca.micarrera.dialog.EditAssigmentDialogFragment;

public class AssignmentInfoActivity extends AppCompatActivity{

    public Assignment assignment;
    public Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        int id = getIntent().getIntExtra(SubjectActivity.EXTRA_ID, -1);

        repo = new Repository(getApplication());
        this.assignment = repo.getAssigmentById(id);
        updateData(assignment);
        this.setTitle(this.assignment.getSubject());
    }

    private void updateData(Assignment assignment) {

        TextView titleTxt = findViewById(R.id.title);
        TextView dateTxt = findViewById(R.id.date);
        TextView descriptionTxt = findViewById(R.id.description);

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
            DialogFragment dialog = new EditAssigmentDialogFragment();
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
            assignment.setDate(year,monthOfYear,dayOfMonth);
            repo.update(assignment);
            recreate();
        }

    };


    View.OnClickListener datePicker = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {

            new DatePickerDialog(AssignmentInfoActivity.this, date, assignment.getyear(),
                    assignment.getmonth(),
                    assignment.getday()).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assignment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_menu_delete) {

            deleteAssignment(this.assignment);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAssignment(Assignment assignment) {
        this.repo.delete(assignment);
    }


}