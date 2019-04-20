package com.example.lucasgaleano.micarrera.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;
import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao {


    //------Subejct----------------------------------------

    @Query("SELECT * from subject_table")
    LiveData<List<Subject>> getAllSubjects();

    @Query("SELECT * FROM subject_table WHERE subject_table.name=:subjectName")
    Subject getSubjectByName(String subjectName);

    @Query("DELETE FROM subject_table")
    void deleteAllSubject();

    @Query("DELETE FROM Predecessor_table")
    void deleteAllPredecessor();

    @Insert
    void insert(SubjectPredecessor... predecessors);

    @Query("UPDATE Subject_table SET state=:state WHERE name=:name ")
    void update(String name, int state);

    @Insert
    void insert(Subject... subjects);

    @Update
    void update(Subject... subjects);

    @Delete
    void delete(Subject... subjects);

    //------Exam----------------------------------------

    @Query("SELECT predecessor FROM predecessor_table WHERE predecessor_table.subject_name=:subjectName")
    List<String> getPredecessorByName(String subjectName);

    @Query("SELECT * FROM exam_table")
    LiveData<List<Exam>> getAllExams();

    @Query("SELECT * FROM exam_table WHERE exam_table.subject=:subjectName")
    LiveData<List<Exam>> getExamsBySubject(String subjectName);

    @Query("SELECT * FROM exam_table WHERE exam_table.date=:dateExam")
    List<Exam> getExamsByDate(Calendar dateExam);

    @Query("SELECT * FROM exam_table WHERE exam_table.id=:id")
    Exam getExamsById(int id);

    @Insert
    void insert(Exam... exams);

    @Update
    void update(Exam... exams);

    @Delete
    void delete(Exam Exams);

    //------Assignment----------------------------------------

    @Insert
    void insert(Assignment... assignments);

    @Update
    void update(Assignment... assignments);

    @Query("SELECT * FROM assignment_table WHERE assignment_table.id=:id")
    Assignment getAssigmentsById(int id);


    @Query("SELECT * FROM assignment_table WHERE assignment_table.subject=:subjectName")
    LiveData<List<Assignment>> getAssigmentsBySubject(String subjectName);


    @Query("SELECT * FROM assignment_table")
    LiveData<List<Assignment>> getAllAssigment();

    @Delete
    void delete(Assignment assignments);

    //------Teacher----------------------------------------

    @Insert
    void insert(Teacher... teachers);

    @Update
    void update(Teacher... teachers);

    @Query("SELECT * FROM teacher_table WHERE teacher_table.id=:id")
    Teacher getTeacherById(int id);

}


