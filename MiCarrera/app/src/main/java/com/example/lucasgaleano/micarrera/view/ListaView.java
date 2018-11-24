package com.example.lucasgaleano.micarrera.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.activities.ActivityInfo;
import com.example.lucasgaleano.micarrera.classes.Calendario;
import com.example.lucasgaleano.micarrera.database.Exam;

import java.util.Calendar;
import java.util.List;


public class ListaView extends LinearLayout {

    private TextView header;
    private Context context;
    private Button add_header_button;
    private float SizeLetra = 20;
    private String titulo;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ListaView(Context context) {
        super(context);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ListaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ListaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init(Context context) {
        this.context = context;
        this.setOrientation(VERTICAL);
        createHeader();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createHeader() {
        LinearLayout headerLayout;
        headerLayout = new LinearLayout(this.context);
        LinearLayout.LayoutParams v = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerLayout.setOrientation(HORIZONTAL);
        headerLayout.setLayoutParams(v);
        headerLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.header, null));
        header = new TextView(context);
        header.setPadding(20, 10, 10, 10);
        header.setTextSize(SizeLetra);
        add_header_button = new Button(this.context);

        add_header_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityInfo.class);
                intent.putExtra("Click", header.getText().toString());
                context.startActivity(intent);
            }});

        add_header_button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add_black_24dp, null));
        add_header_button.setBackgroundResource(R.drawable.selectorimagen);
        add_header_button.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
        headerLayout.addView(header);
        headerLayout.addView(add_header_button);
        this.addView(headerLayout);
    }



    public void setHeader(String Titulo) {
        this.titulo = Titulo;
        header.setText(Titulo);
    }


    public void addItem(String Titulo) {
        TextView item = new TextView(this.context);
        item.setText(Titulo);
        item.setTextSize(SizeLetra * (float) 0.8);
        item.setPadding((int) SizeLetra * 2, 5, 5, 5);
        this.addView(item);
        this.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.header, null));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void update( final List<Exam> exams) {
        cleanAll();
        addAllItem(exams);
    }


    private void addAllItem(List<Exam> exams) {
        String titulo;
        String fecha;

        for (Exam examen : exams)
        {
            titulo=Exam.get(examen.getType());
            fecha= Calendario.formatDate(examen.getDate());
            addItem(titulo+"->"+fecha);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void cleanAll() {
        this.removeAllViews();
        this.createHeader();
        this.setHeader(this.titulo);
    }

}

