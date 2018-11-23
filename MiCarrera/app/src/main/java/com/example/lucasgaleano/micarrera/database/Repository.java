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

    public Repository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mDao = db.Dao();
        mSubject = mDao.getAllSubjects();
        mExam = mDao.getAllExams();
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

    public void updateSubject(Subject subject) {
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


    //------Assignment----------------------------------------

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




}