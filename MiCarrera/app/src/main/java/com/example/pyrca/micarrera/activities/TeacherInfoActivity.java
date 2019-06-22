package com.example.pyrca.micarrera.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.database.Repository;
import com.example.pyrca.micarrera.database.Teacher;
import com.example.pyrca.micarrera.dialog.ChoiceTypeTeacherDialogFragment;
import com.example.pyrca.micarrera.dialog.EditTeacherDialogFragment;
import com.example.pyrca.micarrera.view.NavigationMenu;

public class TeacherInfoActivity extends AppCompatActivity {

    public Repository repo;
    public Teacher teacher;
    private TextView webTxt;
    private TextView emailTxt;
    private TextView descriptionTxt;
    private TextView nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        this.initNavigationAndToolbar();
        int id = getIntent().getIntExtra(SubjectActivity.EXTRA_ID, -1);

        repo = new Repository(getApplication());
        teacher = repo.getTeacherById(id);
        updateData(teacher);

    }


    private void updateData(Teacher teacher) {

        nameTxt = findViewById(R.id.name);
        emailTxt = findViewById(R.id.email);
        descriptionTxt = findViewById(R.id.description);
        webTxt = findViewById(R.id.web);

        webTxt.setText(teacher.getWebSite());
        emailTxt.setText(teacher.getEmail());
        descriptionTxt.setText(Teacher.get(teacher.getType()));
        nameTxt.setText(teacher.getName());

        webTxt.setOnClickListener(clicks);
        nameTxt.setOnClickListener(clicks);
        emailTxt.setOnClickListener(clicks);
        descriptionTxt.setOnClickListener(clicksType);
    }

    View.OnClickListener clicks = new View.OnClickListener() {
        //@SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            TextView texto = (TextView) v;
            DialogFragment dialog = new EditTeacherDialogFragment();
            Bundle args = new Bundle();
            args.putString("EXTRA_TITLE", texto.getResources().getResourceEntryName(texto.getId()));
            args.putString("EXTRA_CONTENT", texto.getText().toString());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"Choose");

        }
    };

    View.OnClickListener clicksType = new View.OnClickListener() {
        //@SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            DialogFragment dialog = new ChoiceTypeTeacherDialogFragment();
            Bundle args = new Bundle();
            args.putInt("TEACHER_ID",teacher.getId());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"Choose");
        }
    };

    private void initNavigationAndToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationMenu Nav = new NavigationMenu(this, drawer);
        navigationView.setNavigationItemSelectedListener(Nav.getListener());
    }

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

            this.repo.delete(teacher);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
