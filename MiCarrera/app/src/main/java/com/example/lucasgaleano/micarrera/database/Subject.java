package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.lucasgaleano.micarrera.R;

@Entity(tableName = "subject_table")
public class Subject {

    @Ignore
    public static final int INHABILITADA = 0;
    @Ignore
    public static final int HABILITADA = 1;
    @Ignore
    public static final int CURSANDO = 2;
    @Ignore
    public static final int CURSADA = 3;
    @Ignore
    public static final int APROBADA = 4;


    @PrimaryKey
    @NonNull
    private String name;

    private int state;
    private int level;
    private int position;
    private String website;
    private String photos;

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean estasAprobada() {
        return this.getState() == this.APROBADA;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }


}
