package com.example.lucasgaleano.micarrera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;

public class ActivityInfo extends AppCompatActivity {
private TextView Texto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();

        Texto=findViewById(R.id.textView1);
        Texto.setText(intent.getStringExtra("Click"));

    }
}
