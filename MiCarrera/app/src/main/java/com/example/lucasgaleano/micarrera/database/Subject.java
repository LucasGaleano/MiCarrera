package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "subject_table")
public class Subject {


    @PrimaryKey
    @NonNull
    private String name;

    private int state;
    private int level;
    private int position;

    public Subject(String name, int state, int level, int position){
        this.name = name;
        this.state = state;
        this.level = level;
        this.position = position;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public int getLevel() {
        return level;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
