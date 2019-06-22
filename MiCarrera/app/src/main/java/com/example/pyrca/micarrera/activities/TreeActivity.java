package com.example.pyrca.micarrera.activities;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.database.Subject;
import com.example.pyrca.micarrera.dialog.SubjectDialogFragment;
import com.example.pyrca.micarrera.view.NavigationMenu;
import com.example.pyrca.micarrera.view.SubjectTreeModel;
import com.example.pyrca.micarrera.view.SubjectTreeView;
import com.example.pyrca.micarrera.view.SubjectView;

import java.util.List;

public class TreeActivity extends AppCompatActivity{

    private final String TAG = TreeActivity.class.getSimpleName();
    private SubjectTreeView mSystemTreeView;
    private SubjectTreeModel treeModel;
    public static final String EXTRA_NAME_SUBJECT = "com.example.pyrca.micarrera.extra.NAME_SUBJECT";
    public static final String EXTRA_STATE_SUBJECT = "com.example.pyrca.micarrera.extra.STATE_SUBJECT";
    private Intent intentSubjectActivity;
    private TreeActivity activity;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        activity = this;

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


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigationAndToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationMenu Nav = new NavigationMenu(this,drawer);
        navigationView.setNavigationItemSelectedListener(Nav.getListener());
    }

    View.OnClickListener clicks = new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            SubjectView sub = (SubjectView) v;
            intentSubjectActivity.putExtra(EXTRA_NAME_SUBJECT, sub.getText().toString());
            if(sub.estasHabilitado()){
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
