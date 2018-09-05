package com.example.lucasgaleano.buttonexample.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.lucasgaleano.buttonexample.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public class SubjectView extends android.support.v7.widget.AppCompatTextView {

    private int level;
    private boolean primary = false;
    private int position;
    private int state = INHABILIDATADA;
    private List<String> predecessor;

    public static final int INHABILIDATADA = 0;
    public static final int HABILITADA = 1;
    public static final int CURSADA = 2;
    public static final int FINAL = 3;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        changeBackground(state);
    }


    private void changeBackground(int state){
        if(state == this.HABILITADA)
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_habilitada));

        if(state == this.INHABILIDATADA)
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_inhabilitada));

        if(state == this.CURSADA)
            ;
    }

    @IntDef({INHABILIDATADA, HABILITADA, CURSADA, FINAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }


    public SubjectView(Context context) {
        this(context, null, 0);
    }

    public SubjectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubjectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public SubjectView(Context context, String name, int level, int position, List<String> predecessor) {
        super(context, null, 0);

        setGravity(Gravity.CENTER);
        setPadding(10,10,10,10);
        changeBackground(state);
        this.setText(name);
        this.setLevel(level);
        this.setPrimary(primary);
        this.position = position;
        if (predecessor == null)
            this.predecessor = Arrays.asList("");
        else
            this.predecessor = predecessor;
    }

    public boolean isPrimary() {
        return primary;
    }


    public void setPrimary(boolean primary) {
        this.primary = primary;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public List<String> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(List<String> correlatives) {
        this.predecessor = correlatives;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}