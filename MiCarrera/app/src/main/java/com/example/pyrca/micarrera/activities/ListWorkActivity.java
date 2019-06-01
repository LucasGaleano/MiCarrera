package com.example.pyrca.micarrera.activities;


import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.database.Assignment;
import com.example.pyrca.micarrera.database.Repository;
import com.example.pyrca.micarrera.view.ListaView;
import com.example.pyrca.micarrera.view.NavigationMenu;

import java.util.List;

public class ListWorkActivity extends AppCompatActivity {

    private Repository repo;
    private ListaView listaTareas;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listwork);
        initNavigationAndToolbar();
        this.repo = new Repository(getApplication());
        this.listaTareas = findViewById(R.id.listwork);

        repo.getAllAssigments().observe(this, new Observer<List<Assignment>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onChanged(@Nullable List<Assignment> assignments) {
                        listaTareas.update(assignments);
                    }
                }
        );

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
        NavigationMenu Nav = new NavigationMenu(this,drawer);
        navigationView.setNavigationItemSelectedListener(Nav.getListener());
    }
}
