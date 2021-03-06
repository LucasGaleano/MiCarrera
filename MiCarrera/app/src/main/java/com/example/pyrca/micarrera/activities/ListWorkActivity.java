package com.example.pyrca.micarrera.activities;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.database.Assignment;
import com.example.pyrca.micarrera.database.Repository;
import com.example.pyrca.micarrera.view.ItemListaView;
import com.example.pyrca.micarrera.view.ListaView;
import com.example.pyrca.micarrera.view.NavigationMenu;

import java.util.List;

public class ListWorkActivity extends AppCompatActivity {

    private Repository repo;
    private ListaView listaTareas;
    public static final String EXTRA_ID = "com.example.pyrca.micarrera.extra.ID";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listwork);
        initNavigationAndToolbar();
        this.repo = new Repository(getApplication());
        this.listaTareas = findViewById(R.id.listwork);
        this.listaTareas.setOnLongCLicks(onLongclickItem);
        this.listaTareas.setOnClicks(onClickItemAssignment);

        repo.getAllAssigments().observe(this, new Observer<List<Assignment>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onChanged(@Nullable List<Assignment> assignments) {
                        listaTareas.update(assignments);
                    }
                }
        );
    }

    View.OnLongClickListener onLongclickItem = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Assignment aux = (Assignment) ((ItemListaView) v).getItem();
            repo.delete(aux);
            return true;
        }
    };

    View.OnClickListener onClickItemAssignment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentAssigment;
            intentAssigment = new Intent(v.getContext(), AssignmentInfoActivity.class);
            Assignment aux = (Assignment) ((ItemListaView) v).getItem();
            intentAssigment.putExtra(EXTRA_ID, aux.getId());
            startActivity(intentAssigment);
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
        NavigationMenu Nav = new NavigationMenu(this,drawer);
        navigationView.setNavigationItemSelectedListener(Nav.getListener());
    }
}
