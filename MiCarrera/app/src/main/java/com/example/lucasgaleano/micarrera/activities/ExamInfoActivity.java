package com.example.lucasgaleano.micarrera.activities;

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

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.classes.Calendario;
import com.example.lucasgaleano.micarrera.database.Exam;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.dialog.EditAssigmentDialogFragment;
import com.example.lucasgaleano.micarrera.dialog.EditExamDialogFragment;

public class ExamInfoActivity extends AppCompatActivity {

    public TextView titleTxt;
    private TextView dateTxt;
    private TextView descriptionTxt;
    public Repository repo;
    public Exam exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exam);


        int id = getIntent().getIntExtra(SubjectActivity.EXTRA_ID,-1);

        repo = new Repository(getApplication());
        exam = repo.getExamById(id);
        updateData(exam);
    }

    private void updateData(Exam exam) {

        titleTxt = findViewById(R.id.title);
        dateTxt = findViewById(R.id.date);
        descriptionTxt = findViewById(R.id.description);

        titleTxt.setText( exam.title());
        dateTxt.setText(Calendario.formatDate(exam.getDate()));
        descriptionTxt.setText(exam.getDescription());

        titleTxt.setOnClickListener(clicks);
        dateTxt.setOnClickListener(datePicker);
        descriptionTxt.setOnClickListener(clicks);

    }

    View.OnClickListener clicks = new View.OnClickListener() {
        //@SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            TextView texto = (TextView) v;
            DialogFragment dialog = new EditExamDialogFragment();
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
            exam.setDate(year,monthOfYear,dayOfMonth);
            repo.update(exam);
            recreate();
        }

    };

    View.OnClickListener datePicker = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {

            new DatePickerDialog(ExamInfoActivity.this, date, exam.getyear(),
                    exam.getmonth(),
                    exam.getday()).show();
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

            this.repo.delete(this.exam);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


