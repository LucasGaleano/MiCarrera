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
import com.example.lucasgaleano.micarrera.database.Exam;

import java.util.Calendar;
import java.util.List;


public class ListaView extends LinearLayout {

    private TextView header;
    private Context context;
    private Button add_header_button;
    private float SizeLetra = 20;
    private Intent intentParciales,intentProfesores,intentTareas;

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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View arg0) {
                //addItem("item2");
                if (header.getText().toString()=="Parciales") {
                    intentParciales = new Intent(getContext(), ActivityInfo.class);
                    intentParciales.putExtra("Click", "Parciales");
                    context.startActivity(intentParciales);
                }
                if (header.getText().toString()=="Profesores") {
                    intentProfesores = new Intent(getContext(), ActivityInfo.class);
                    intentProfesores.putExtra("Click", "Profesores");
                    context.startActivity(intentProfesores);
                }
                if (header.getText().toString()=="Tareas") {
                    intentTareas = new Intent(getContext(), ActivityInfo.class);
                    intentTareas.putExtra("Click", "Tareas");
                    context.startActivity(intentTareas);
                }
            }
        });
        add_header_button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add_black_24dp, null));
        add_header_button.setBackgroundResource(R.drawable.selectorimagen);
        add_header_button.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
        headerLayout.addView(header);
        headerLayout.addView(add_header_button);
        this.addView(headerLayout);
    }

    public void setHeader(String Titulo) {
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
            fecha=formatDate(examen.getDate());
            addItem(titulo+"->"+fecha);
        }

    }

    private void cleanAll() {
        this.removeView(this);
       // this.findViewById(5).setVisibility(View.GONE);

    }



    private String formatDate(Calendar cal) {

        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).concat(
                "/"+ String.valueOf(cal.get(Calendar.MONTH)).concat(
                        "/"+ String.valueOf(cal.get(Calendar.YEAR))));

    }

}

