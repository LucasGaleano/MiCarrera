package com.example.lucasgaleano.micarrera.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;

public class activity_assigment extends Activity {
private TextView Materia,Fecha,Descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment);
        Intent intent = getIntent();

        Materia = findViewById(R.id.Materia);
        Fecha = findViewById(R.id.Fecha);
        Descripcion = findViewById(R.id.Descripcion);

        Materia.setText(intent.getStringExtra("Materia"));
        Fecha.setText(intent.getStringExtra("Fecha"));
        Descripcion.setText(intent.getStringExtra("Descripcion"));
    }
}
