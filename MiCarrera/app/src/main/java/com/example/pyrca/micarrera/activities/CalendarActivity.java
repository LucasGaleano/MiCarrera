package com.example.pyrca.micarrera.activities;


import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.classes.Calendario;
import com.example.pyrca.micarrera.database.Exam;
import com.example.pyrca.micarrera.database.Repository;
import com.example.pyrca.micarrera.view.ListaView;
import com.example.pyrca.micarrera.view.NavigationMenu;

import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListaView listaEventos;
    private List<Exam> listaExamenes;
    private CalendarView calendarioEventos;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        this.setTitle("Calendario");
        initNavigationAndToolbar();
        repo = new Repository(getApplication());
        listaExamenes = new ArrayList<>();
        listaEventos = findViewById(R.id.listaEventos);
        calendarioEventos = findViewById(R.id.calendarioEventos);
        listaEventos.setHeader("Evento");


        calendarioEventos.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                listaEventos.update(Calendario.filtrarExamenesPorFecha(listaExamenes,year,month,day));
            }
        });



        repo.getAllExam().observe(this, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable final List<Exam> exams) {
                listaExamenes.clear();
                listaExamenes.addAll(exams);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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
        NavigationMenu nav = new NavigationMenu(this,drawer);
        navigationView.setNavigationItemSelectedListener(nav.getListener());
    }
}
