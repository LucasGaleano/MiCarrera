package com.example.pyrca.micarrera.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pyrca.micarrera.R;
import com.example.pyrca.micarrera.database.Assignment;
import com.example.pyrca.micarrera.database.Exam;
import com.example.pyrca.micarrera.database.Repository;
import com.example.pyrca.micarrera.database.Subject;
import com.example.pyrca.micarrera.database.Teacher;
import com.example.pyrca.micarrera.view.FotosAdapter;
import com.example.pyrca.micarrera.view.ItemListaView;
import com.example.pyrca.micarrera.view.ListaView;
import com.example.pyrca.micarrera.view.NavigationMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SubjectActivity extends AppCompatActivity {


    private String subjectName;
    private Repository repo;
    private ListaView listaTareas, listaProfesores, listaExamenes;
    public static final String EXTRA_ID = "com.example.pyrca.micarrera.extra.ID";
    public FloatingActionButton fab_action;
    public ImageView foto;
    public File file;
    public String pictureImagePath = "";
    public String carpeta = "/storage/emulated/0/Pictures/Ficus/";
    public Uri outputFileUri;
    private Subject subject;
    public Bitmap d,scaled;
    public List<String> listaFotos;
    public GridView gridView;
    private SubjectActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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
        foto = findViewById(R.id.camara);
        gridView = (GridView) findViewById(R.id.baseGridView);

        if (ContextCompat.checkSelfPermission(SubjectActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SubjectActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(SubjectActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        22);
            }
        } else {

        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedItem = listaFotos.get(position);
                Toast.makeText(getApplicationContext(),selectedItem, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + selectedItem), "image/*");
                startActivity(intent);

            }
        });


        fab_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                pictureImagePath = storageDir.getAbsolutePath() + "/Ficus/" + subjectName + "/" + subjectName +"-"+ n +".jpg";

                if (ContextCompat.checkSelfPermission(SubjectActivity.this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(SubjectActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(SubjectActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }
                } else {

                }


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


        repo.getAssigmentsBySubject(subjectName).observe(this, new Observer<List<Assignment>>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChanged(final List<Assignment> assignments) {
                listaTareas.update(assignments);
            }
        });

        repo.getTeacherBySubject(subjectName).observe(this, new Observer<List<Teacher>>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChanged(@Nullable List<Teacher> teachers) {
                listaProfesores.update(teachers);
            }
        });


    }


    private void setListas() {

        listaTareas.setHeader("Tareas");
        listaTareas.setAddClick(onClickAddAssignmentItem);
        listaTareas.setOnClicks(onClickItemTareas);
        listaTareas.setOnLongCLicks(onLongClickItemTareas);


        listaProfesores.setHeader("Profesores");
        listaProfesores.setOnClicks(onClickItemProfesores);
        listaProfesores.setAddClick(onClickAddTeacherItem);
        listaProfesores.setOnLongCLicks(onLongClickItemProfesores);


        listaExamenes.setHeader("Examenes");
        listaExamenes.setOnClicks(onClickItemExam);
        listaExamenes.setAddClick(onClickAddExamItem);
        listaExamenes.setOnLongCLicks(onLongClickItemExamenes);

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
            Teacher aux = (Teacher) ((ItemListaView) v).getItem();
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

    View.OnLongClickListener onLongClickItemExamenes = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Exam aux = (Exam) ((ItemListaView) view).getItem();
            repo.delete(aux);
            return true;
        }
    };

    View.OnLongClickListener onLongClickItemProfesores = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Teacher aux = (Teacher) ((ItemListaView) view).getItem();
            repo.delete(aux);
            return true;
        }
    };

    View.OnClickListener onClickAddAssignmentItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Assignment newAssignment = new Assignment(subjectName);
            repo.update(newAssignment);

        }
    };

    View.OnClickListener onClickAddExamItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Exam newExam = new Exam(subjectName);
            repo.update(newExam);

        }
    };

    View.OnClickListener onClickAddTeacherItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Teacher newTeacher = new Teacher(subjectName);
            repo.update(newTeacher);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        super.onActivityResult(requestCode, resultcode, data);
        recreate();
        if(requestCode!=0) {
            d = new BitmapDrawable(this.getResources(), file.getAbsolutePath()).getBitmap();
            int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
            scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
            foto.setImageBitmap(scaled);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    file = new File(pictureImagePath);
                    outputFileUri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(intent, 0);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
            case 22:
                File f = new File(carpeta+subjectName);
                if(!f.isDirectory()) {
                    String dir = carpeta + subjectName;
                    File imageDirectory = new File(dir);
                    imageDirectory.mkdirs();
                }
                listaFotos=ReadSDCard();
                gridView.setAdapter(new FotosAdapter(this,listaFotos));
                return;

            case 99:
                return;
        }
    }

    private List<String> ReadSDCard()
    {
        //It have to be matched with the directory in SDCard
        File f = new File(carpeta+subjectName);

        File[] files=f.listFiles();
        List<String> tFileList = new ArrayList<String>();

        try {

        for(int i=0; i<files.length; i++)
        {
            File file = files[i];
            /*It's assumed that all file in the path are in supported type*/
            String filePath = file.getPath();
            if(filePath.endsWith(".jpg")) // Condition to check
                tFileList.add(filePath);
        }

        }
        catch(Exception e) {
            System.out.println("Null pointer exception");
        }

        return tFileList;
    }
}




