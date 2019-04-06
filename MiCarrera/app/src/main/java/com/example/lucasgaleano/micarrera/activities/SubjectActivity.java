package com.example.lucasgaleano.micarrera.activities;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.view.ItemListaView;
import com.example.lucasgaleano.micarrera.database.Assignment;
import com.example.lucasgaleano.micarrera.database.Exam;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.database.Subject;
import com.example.lucasgaleano.micarrera.database.teacher;
import com.example.lucasgaleano.micarrera.view.ListaView;
import com.example.lucasgaleano.micarrera.view.NavigationMenu;

import java.io.File;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {


    private String subjectName;
    private Repository repo;
    private ListaView listaTareas, listaProfesores, listaExamenes;
    private DrawerLayout drawer;
    public static final String EXTRA_ID = "com.example.lucasgaleano.micarrera.extra.ID";
    public FloatingActionButton fab_action;
    public ImageView foto;
    public File file;
    public String pictureImagePath = "";
    public Uri outputFileUri;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        initNavigationAndToolbar();

        repo = new Repository(getApplication());
        final Intent intent = getIntent();
        subjectName = intent.getStringExtra(TreeActivity.EXTRA_NAME_SUBJECT);

        this.subject = repo.getSubjectByName(subjectName);

        listaTareas = findViewById(R.id.listaTareas);
        listaProfesores = findViewById(R.id.listaProfesores);
        listaExamenes = findViewById(R.id.listaExamenes);

        this.setTitle(subjectName);
        setListas();

        fab_action = findViewById(R.id.fab);

        fab_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                pictureImagePath = storageDir.getAbsolutePath() + "/" + subjectName+".jpg";
                file = new File(pictureImagePath);
                outputFileUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, 0);
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


        repo.getAllAssigments().observe(this, new Observer<List<Assignment>>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChanged(final List<Assignment> assignments) {
                listaTareas.update(assignments);
            }
        });


    }


    private void setListas() {

        listaTareas.setHeader("Tareas");
        listaTareas.setAddClick(onClickAddItem);
        listaTareas.setOnClicks(onClickItemTareas);
        listaTareas.setOnLongCLicks(onLongClickItemTareas);


        listaProfesores.setHeader("Profesores");
        listaProfesores.setOnClicks(onClickItemProfesores);

        listaExamenes.setHeader("Examenes");
        listaExamenes.setOnClicks(onClickItemExam);

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
        NavigationMenu Nav = new NavigationMenu(this, drawer);
        navigationView.setNavigationItemSelectedListener(Nav.getListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dejarCursar) {
            this.subject.setState(Subject.HABILITADA);
        }
        if (id == R.id.action_aprobada) {
            this.subject.setState(Subject.APROBADA);
        }

        this.repo.update(this.subject);
        this.onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickItemExam = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentExam;
            intentExam = new Intent(v.getContext(), ExamInfoActivity.class);
            Exam aux = (Exam) ((ItemListaView) v).getItem();
            intentExam.putExtra(EXTRA_ID, aux.getId());
            startActivity(intentExam);
        }
    };

    View.OnClickListener onClickItemProfesores = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentProfesores;
            intentProfesores = new Intent(v.getContext(), TeacherInfoActivity.class);
            teacher aux = (teacher) ((ItemListaView) v).getItem();
            intentProfesores.putExtra(EXTRA_ID, aux.getId());
            startActivity(intentProfesores);
        }
    };

    View.OnClickListener onClickItemTareas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentTareas;
            intentTareas = new Intent(v.getContext(), AssignmentInfoActivity.class);
            Assignment aux = (Assignment) ((ItemListaView) v).getItem();
            intentTareas.putExtra(EXTRA_ID, aux.getId());
            startActivity(intentTareas);

        }
    };

    View.OnLongClickListener onLongClickItemTareas = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Assignment aux = (Assignment) ((ItemListaView) view).getItem();
            repo.delete(aux);
            return true;
        }
    };

    View.OnClickListener onClickAddItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Assignment newAssignment = new Assignment(subjectName);
            repo.update(newAssignment);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        super.onActivityResult(requestCode, resultcode, data);

        Bitmap d = new BitmapDrawable(this.getResources() , file.getAbsolutePath()).getBitmap();
        int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
        foto.setImageBitmap(scaled);
    }
}




