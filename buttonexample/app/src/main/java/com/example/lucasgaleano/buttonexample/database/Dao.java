package com.example.lucasgaleano.buttonexample.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lucasgaleano.buttonexample.view.SubjectView;

import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao {


    @Query("SELECT * from subject_table")
    LiveData<List<Subject>> getAllSubjects();

    @Query("SELECT name FROM subjectpredecessor INNER JOIN subject_table " + "ON subjectpredecessor.subject_name=:subjectName")
    LiveData<List<String>> getAllPredecessor(String subjectName);

    @Query("DELETE FROM subject_table")
    void deleteAll();

    @Update
    void update(Subject... subject);

    @Insert
    void insert(Subject... subject);

}


