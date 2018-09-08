package com.example.lucasgaleano.buttonexample.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Subject.class,
        parentColumns = "name",
        childColumns = "subject_name"),
        primaryKeys = {"subject_name","predecessor"},
        tableName = "Predecessor_table")
public class SubjectPredecessor {



    @NonNull
    @ColumnInfo(name = "subject_name")
    private String subject;

    @NonNull
    private String predecessor;

    public SubjectPredecessor(String subject, String predecessor){
        this.subject = subject;
        this.predecessor = predecessor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(String predecessor) {
        this.predecessor = predecessor;
    }
}
