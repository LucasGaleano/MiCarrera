package com.example.lucasgaleano.micarrera.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
        mExam = mDao.getAllExam();
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return mSubject;
    }

    public LiveData<List<Exam>> getAllExam() {
        return mExam;
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
        new updateAsyncTask(mDao).execute(subject);
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

    private class updateAsyncTask extends AsyncTask<Subject, Void, Void> {

        private Dao dao;

        public updateAsyncTask(Dao mDao) {
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
}