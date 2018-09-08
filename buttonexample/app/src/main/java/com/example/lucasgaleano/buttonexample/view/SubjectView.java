package com.example.lucasgaleano.buttonexample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.lucasgaleano.buttonexample.R;

import java.util.Arrays;
import java.util.List;

public class SubjectView extends android.support.v7.widget.AppCompatTextView {

    private int level;
    private int position;
    private int state;
    private List<String> predecessor;




    private void changeBackground(int state) {
        if (state == getResources().getInteger(R.integer.INHABILITADA))
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_inhabilitada));

        if (state == getResources().getInteger(R.integer.HABILITADA))
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_habilitada));

        if (state == getResources().getInteger(R.integer.CURSANDO))
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_cursando));

        if (state == getResources().getInteger(R.integer.CURSADA))
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_cursada));

        if (state == getResources().getInteger(R.integer.APROBADA))
            setBackgroundDrawable(getResources().getDrawable(R.drawable.background_subject_aprobada));

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
        setPadding(10, 10, 10, 10);
        this.setState(getResources().getInteger(R.integer.INHABILITADA));
        this.setText(name);
        this.setLevel(level);
        this.setPosition(position);
        if (predecessor == null)
            this.predecessor = Arrays.asList("");
        else
            this.predecessor = predecessor;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        changeBackground(state);
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