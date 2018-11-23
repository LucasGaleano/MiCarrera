package com.example.lucasgaleano.micarrera.activities;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.database.Exam;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.view.ListaView;
import com.example.lucasgaleano.micarrera.view.NavigationMenu;

import java.util.ArrayList;
import java.util.Calendar;
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

        initNavigationAndToolbar();
        listaExamenes = new ArrayList<>();
        repo = new Repository(getApplication());
        listaEventos = findViewById(R.id.listaEventos);
        listaEventos.setHeader("Evento");
        calendarioEventos = findViewById(R.id.calendarioEventos);

        calendarioEventos.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar aux = Calendar.getInstance();
                aux.set(i,i1,i2);
                Log.d("date",formatDate(aux));
                listaEventos.update(listaExamenes);
            }
        });

        repo.getAllExam().observe(this, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable final List<Exam> exams) {
                listaExamenes.clear();
                for(Exam examen : exams){
                    listaExamenes.add(examen);
                }
            }
        });


    }

    private String formatDate(Calendar cal) {

        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).concat(
                "/"+ String.valueOf(cal.get(Calendar.MONTH)).concat(
                        "/"+ String.valueOf(cal.get(Calendar.YEAR))));

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
