package com.example.lucasgaleano.micarrera.activities;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.database.Exam;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.database.Subject;
import com.example.lucasgaleano.micarrera.view.ListaView;
import com.example.lucasgaleano.micarrera.view.NavigationMenu;

import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    private Switch switchAprobada;
    private String subjectName;
    private Repository repo;
    private Subject sub;
    private int subjectState;
    private ListaView listaTareas, listaProfesores, listaExamenes;
    private DrawerLayout drawer;
    private Intent intentExam,intentProfesores,intentTareas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        initNavigationAndToolbar();

        repo = new Repository(getApplication());
        Intent intent = getIntent();
        subjectState = intent.getIntExtra(TreeActivity.EXTRA_STATE_SUBJECT,-1);
        subjectName = intent.getStringExtra(TreeActivity.EXTRA_NAME_SUBJECT);

        sub = new Subject(subjectName,
                subjectState,0,0);

        listaTareas = findViewById(R.id.listaTareas);
        listaProfesores = findViewById(R.id.listaProfesores);
        listaExamenes = findViewById(R.id.listaExamenes);

        listaExamenes.setOnClicks(onClickItemExam);
        listaProfesores.setOnClicks(onClickItemProfesores);
        listaTareas.setOnClicks(onClickItemTareas);

        intentExam = new Intent(this,activity_assigment.class);
        intentProfesores = new Intent(this,activity_assigment.class);
        intentTareas= new Intent(this,activity_assigment.class);

        this.setTitle(subjectName);
        setSwichts();
        setListas();


        switchAprobada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                    sub.setState(getResources().getInteger(R.integer.APROBADA));
                else
                    sub.setState(getResources().getInteger(R.integer.HABILITADA));
                repo.updateSubject(sub);

            }
        });

        repo.getExamsBySubject(subjectName).observe(this, new Observer<List<Exam>>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onChanged(final List<Exam> exams) {
                    listaExamenes.update(exams);
                    }
                });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                listaTareas.addItem("Audio");
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                listaExamenes.addItem("Foto");
            }
        });

    }

    private void setListas() {

        listaTareas.setHeader("Tareas");
        listaProfesores.setHeader("Profesores");
        listaExamenes.setHeader("Examenes");

    }

    private void setSwichts() {

        switchAprobada = findViewById(R.id.switchAprobada);
        if(sub.getState() != getResources().getInteger(R.integer.APROBADA))
            switchAprobada.setChecked(false);
        else
            switchAprobada.setChecked(true);



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



    View.OnClickListener onClickItemExam = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(intentExam);
        }
    };

    View.OnClickListener onClickItemProfesores = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(intentProfesores);
        }
    };

    View.OnClickListener onClickItemTareas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            intentTareas.putExtra("Materia",v.toString());
            intentTareas.putExtra("Fecha","Fecha:");
            intentTareas.putExtra("Descripcion","Descripcion:");

            startActivity(intentTareas);

        }
    };

}
