package com.example.lucasgaleano.micarrera.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.lucasgaleano.micarrera.database.Subject;
import com.example.lucasgaleano.micarrera.dialog.SubjectDialogFragment;
import com.example.lucasgaleano.micarrera.view.SubjectTreeModel;
import com.example.lucasgaleano.micarrera.view.SubjectTreeView;
import com.example.lucasgaleano.micarrera.view.SubjectView;
import com.example.lucasgaleano.micarrera.R;

import java.util.List;

public class TreeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private final String TAG = TreeActivity.class.getSimpleName();
    private SubjectTreeView mSystemTreeView;
    private SubjectTreeModel treeModel;
    public static final String EXTRA_NAME_SUBJECT = "com.example.lucasgaleano.micarrera.extra.NAME_SUBJECT";
    public static final String EXTRA_STATE_SUBJECT = "com.example.lucasgaleano.micarrera.extra.STATE_SUBJECT";
    private Intent intentSubjectActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        initNavigationAndToolbar();


        treeModel = ViewModelProviders.of(this).get(SubjectTreeModel.class);
        mSystemTreeView = findViewById(R.id.treeView);
        mSystemTreeView.setOnClicks(clicks);
        intentSubjectActivity = new Intent(this, SubjectActivity.class);

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

    private void initNavigationAndToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_materias) {

        } else if (id == R.id.nav_Tareas) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
