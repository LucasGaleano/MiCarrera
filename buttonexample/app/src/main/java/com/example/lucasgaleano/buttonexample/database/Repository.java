package com.example.lucasgaleano.buttonexample.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {

    private Dao mDao;
    private LiveData<List<Subject>> mSubject;

    public Repository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mDao = db.Dao();
        mSubject = mDao.getAllSubjects();
    }

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
        new updateAsyncTask(mDao).execute(subject);
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
}