package com.example.lucasgaleano.micarrera.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.database.Subject;

import java.util.List;

public class SubjectTreeModel extends AndroidViewModel {


    private final Repository mRepository;
    private LiveData<List<Subject>> subjects;


    public SubjectTreeModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        subjects = mRepository.getAllSubjects();
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public List<String> getPredecessor(String subjectName){
        return mRepository.getPredecessorByName(subjectName);
    }

    public void updateSubject(String name, int level, int position, int state){
        mRepository.update(new Subject(name,state,level,position));
    }

    public void updateSubject(String name,int state){
        mRepository.update(new Subject(name,state,0,0));
    }

    private Subject getSubjectByName(String name) {
        return mRepository.getSubjectByName(name);
    }

}
