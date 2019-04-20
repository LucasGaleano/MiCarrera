package com.example.lucasgaleano.micarrera.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {

    private Dao mDao;
    private LiveData<List<Subject>> mSubject;
    private LiveData<List<Exam>> mExam;
    private LiveData<List<Assignment>> mAssigment;

    public Repository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mDao = db.Dao();
        mSubject = mDao.getAllSubjects();
        mExam = mDao.getAllExams();
        mAssigment = mDao.getAllAssigment();
    }

    //------Subjects----------------------------------------

    public LiveData<List<Subject>> getAllSubjects() {
        return mSubject;
    }

    public List<String> getPredecessorByName(String subjectName) {
        try {
            return new getPredecessorByNameAsyncTask(mDao).execute(subjectName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void update(Subject subject) {
        new updateSubjectAsyncTask(mDao).execute(subject);
    }

    public Subject getSubjectByName(String name) {
        try {
            return new getSubjectByNameAsyncTask(mDao).execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getPredecessorByNameAsyncTask extends AsyncTask<String, Void, List<String>> {

        private Dao mDao;


        getPredecessorByNameAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            return mDao.getPredecessorByName(strings[0]);
        }
    }

    private class updateSubjectAsyncTask extends AsyncTask<Subject, Void, Void> {

        private Dao dao;

        public updateSubjectAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            dao.update(subjects[0].getName(),subjects[0].getState());
            return null;
        }
    }

    private class getSubjectByNameAsyncTask extends AsyncTask<String, Void, Subject>{
        private Dao dao;

        public getSubjectByNameAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected Subject doInBackground(String... strings) {
            return dao.getSubjectByName(strings[0]);

        }
    }

    //------Exam----------------------------------------
    public Exam getExamById(int id){
        try {
            return new getExamByIdAsyncTask(mDao).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getExamByIdAsyncTask  extends AsyncTask<Integer, Void, Exam> {
        private Dao dao;

        public getExamByIdAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected Exam doInBackground (Integer... integers) {
            return dao.getExamsById(integers[0]);
        }
    }

    public LiveData<List<Exam>> getAllExam() {
        return mExam;
    }

    public LiveData<List<Exam>> getExamsBySubject(String subjectName){

        try {
            return new getExamsBySubjectAsyncTask(mDao).execute(subjectName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getExamsBySubjectAsyncTask  extends AsyncTask<String, Void, LiveData<List<Exam>>> {
        private Dao dao;
        public getExamsBySubjectAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected LiveData<List<Exam>> doInBackground(String... strings) {
            return dao.getExamsBySubject(strings[0]);
        }
    }

    public List<Exam> getExamsByDate(Calendar date) {
        try {
            return new getExamsByDateAsyncTask(mDao).execute(date).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getExamsByDateAsyncTask extends AsyncTask<Calendar, Void, List<Exam>> {

        private Dao mDao;


        getExamsByDateAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected List<Exam> doInBackground(Calendar... calendars) {
            return mDao.getExamsByDate(calendars[0]);
        }
    }

    public void update(Exam exam) {
        new updateExamAsyncTask(mDao).execute(exam);
    }

    private class updateExamAsyncTask extends AsyncTask<Exam, Void, Void> {

        private Dao mDao;

        updateExamAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            Exam exam = mDao.getExamsById(exams[0].getId());
            if(exam == null)
                mDao.insert(exams[0]);
            else
                mDao.update(exams[0]);
            return null;
        }
    }

    public void delete(Exam exam) {
        new deleteExamAsyncTask(mDao).execute(exam);
    }

    private class deleteExamAsyncTask extends AsyncTask<Exam, Void, Void> {

        private Dao mDao;
        deleteExamAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            mDao.delete(exams[0]);
            return null;
        }
    }


    //------Assignment----------------------------------------

    public void update(Assignment assignment){
        try {
            new updateAssignmentAsyncTask(mDao).execute(assignment).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class updateAssignmentAsyncTask extends AsyncTask<Assignment, Void, Void> {

        private Dao mDao;


        updateAssignmentAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Assignment... assignments) {
            Assignment assigment = mDao.getAssigmentsById(assignments[0].getId());
            if(assigment == null)
                mDao.insert(assignments[0]);
            else
                mDao.update(assignments[0]);
            return null;
        }
    }

    public Assignment getAssigmentById(int id){
        try {
            return new getAssigmentByIdAsyncTask(mDao).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Assignment>> getAllAssigments() {
        return mAssigment;
    }

    public LiveData<List<Assignment>> getAssigmentsBySubject(String subjectName){
        try {
            return new getAssignmentBySubjectAsyncTask(mDao).execute(subjectName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getAssigmentByIdAsyncTask  extends AsyncTask<Integer, Void, Assignment> {
        private Dao dao;

        public getAssigmentByIdAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected Assignment doInBackground (Integer... integers) {
            return dao.getAssigmentsById(integers[0]);
        }
    }

    private class getAssignmentBySubjectAsyncTask  extends AsyncTask<String, Void, LiveData<List<Assignment>>> {
        private Dao dao;

        public getAssignmentBySubjectAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected LiveData<List<Assignment>> doInBackground(String... strings) {
            return dao.getAssigmentsBySubject(strings[0]);
        }
    }

    public void delete(Assignment assignment) {
        new deleteAssignmentAsyncTask(mDao).execute(assignment);
    }

    private class deleteAssignmentAsyncTask extends AsyncTask<Assignment, Void, Void> {

        private Dao mDao;
        deleteAssignmentAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Assignment... assignments) {
            mDao.delete(assignments[0]);
            return null;
        }
    }


    //------Teacher----------------------------------------

    public void update(Teacher teacher){
        try {
            new updateTeacherAsyncTask(mDao).execute(teacher).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class updateTeacherAsyncTask extends AsyncTask<Teacher, Void, Void> {

        private Dao mDao;


        updateTeacherAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Teacher... teachers) {
            Teacher teacher = mDao.getTeacherById(teachers[0].getId());
            if(teacher == null)
                mDao.insert(teachers[0]);
            else
                mDao.update(teachers[0]);
            return null;
        }
    }

    public Teacher getTeacherById(int id){
        try {
            return new getTeacherByIdAsyncTask(mDao).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getTeacherByIdAsyncTask  extends AsyncTask<Integer, Void, Teacher> {
        private Dao dao;

        public getTeacherByIdAsyncTask(Dao mDao) {
            dao = mDao;
        }

        @Override
        protected Teacher doInBackground (Integer... integers) {
            return dao.getTeacherById(integers[0]);
        }
    }

    public void delete(Teacher teacher) {
        new deleteTeacherAsyncTask(mDao).execute(teacher);
    }

    private class deleteTeacherAsyncTask extends AsyncTask<Teacher, Void, Void> {

        private Dao mDao;
        deleteTeacherAsyncTask(Dao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Teacher... Teachers ) {
            mDao.delete(Teachers[0]);
            return null;
        }
    }

}