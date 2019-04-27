package com.example.pyrca.micarrera.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pyrca.micarrera.R;

import java.util.List;


public class ListaView extends LinearLayout {

    private TextView header;
    private Context context;
    private Button add_header_button;
    private float SizeLetra = 20;
    private String titulo;
    private OnClickListener onClick;
    private OnClickListener addClick;
    private OnLongClickListener onLongClick;

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

        add_header_button.setOnClickListener(addClick);

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


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addItem(Object item) {
        ItemListaView lItem = new ItemListaView(context);
        lItem.setBackgroundColor(getResources().getColor(R.color.Blanco)) ;
        final ColorStateList backgroundTintList = AppCompatResources.getColorStateList(context, R.color.Blanco);
        ViewCompat.setBackgroundTintList(lItem, backgroundTintList);
        lItem.setTextSize(SizeLetra * (float) 0.8);
        lItem.setPadding((int) SizeLetra * 2, 5, 5, 5);
        lItem.setOnClickListener(this.onClick);
        lItem.setOnLongClickListener(this.onLongClick);
        lItem.additem(item);
        this.addView(lItem);
        this.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.header, null));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void update(List<?> items) {
        cleanAll();
        addAllItem(items);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addAllItem(List<?> items) {

        for (Object item : items)
            this.addItem(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void cleanAll() {
        this.removeAllViews();
        this.createHeader();
        this.setHeader(this.titulo);
    }

    public void setOnClicks(OnClickListener onClicks) {
        this.onClick = onClicks;
    }
    public void setOnLongCLicks(OnLongClickListener onClicks) {
        this.onLongClick = onClicks;
    }

    public void setAddClick(OnClickListener onClicks){
        this.addClick = onClicks;
    }
}

