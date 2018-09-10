package com.example.lucasgaleano.micarrera.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lucasgaleano.micarrera.view.SubjectView;

import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao {


    @Query("SELECT * from subject_table")
    LiveData<List<Subject>> getAllSubjects();

    @Query("SELECT predecessor FROM predecessor_table WHERE predecessor_table.subject_name=:subjectName")
    List<String> getPredecessorByName(String subjectName);

    @Query("SELECT * FROM exam_table")
    LiveData<List<Exam>> getAllExam();

    @Query("SELECT * FROM subject_table WHERE subject_table.name=:subjectName")
    Subject getSubjectByName(String subjectName);

    @Query("DELETE FROM subject_table")
    void deleteAllSubject();

    @Query("DELETE FROM Predecessor_table")
    void deleteAllPredecessor();

    @Update
    void update(Subject... subject);

    @Query("UPDATE Subject_table SET state=:state WHERE name=:name ")
    void update(String name, int state);

    @Insert
    void insert(Exam... exams);

    @Insert
    void insert(Subject... subjects);

    @Insert
    void insert(SubjectPredecessor... predecessors);


}


