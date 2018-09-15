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
        mRepository.updateSubject(new Subject(name,state,level,position));
    }

    public void updateSubject(String name,int state){
        mRepository.updateSubject(new Subject(name,state,0,0));
    }

    private Subject getSubjectByName(String name) {
        return mRepository.getSubjectByName(name);
    }

    //    public void makeIngSystemTree() {
//
//        //LEVEL 1
//        subjects.add(new SubjectView( getApplication().getBaseContext(),"Fisica I", 1, 1, null));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Arq", 1, 2, null));
//        subjects.add(new SubjectView( getApplication().getBaseContext(),"AGA", 1, 3, null));
//        subjects.add(new SubjectView( getApplication().getBaseContext(),"AM I", 1, 4, null));
//        subjects.add(new SubjectView( getApplication().getBaseContext(),"SyO", 1, 5, null));
//        subjects.add(new SubjectView( getApplication().getBaseContext(),"AyED", 1, 6, null));
//        subjects.add(new SubjectView( getApplication().getBaseContext(),"MD", 1, 7, null));
////        subjects.add(new SubjectView(  getApplication().getBaseContext(),"Quimica",1,8,  null));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(),"Ingles I",1,9,  null));
////        subjects.add(new SubjectView(  getApplication().getBaseContext(),"IyS",1,10, null));
////        subjects.add(new SubjectView(  getApplication().getBaseContext(),"SDR",1,11, null));
//
//        //LEVEL 2
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Fisica II", 2, 1, Arrays.asList("AM I", "Fisica I")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "ProA", 2, 3, Arrays.asList("AM I", "AGA")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "AM II", 2, 4, Arrays.asList("AM I", "AGA")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "AdS", 2, 5, Arrays.asList("SyO", "AyED")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "SSL", 2, 6, Arrays.asList("MD", "AyED")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "PdeP", 2, 7, Arrays.asList("MD", "AyED")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "SO", 2, 8, Arrays.asList("MD", "AyED", "Arq")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(),"Ingles II",2,9, Arrays.asList("Ingles I")));
////
////        //LEVEL 3
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "MS", 3, 3, Arrays.asList("AM II")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Economia", 3, 4, Arrays.asList("AdS")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "DDS", 3, 5, Arrays.asList("AdS", "PdeP")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "GdD", 3, 6, Arrays.asList("AdS", "SSL", "PdeP")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Legis", 3, 7, Arrays.asList("AdS", "IyS")));
////
//////        //LEVEL 4
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Sim", 4, 1, Arrays.asList("ProA", "MS")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "IO", 4, 2, Arrays.asList("ProA", "MS")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Com", 4, 3, Arrays.asList("ProA", "Fisica II", "Arq")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "TdC", 4, 4, Arrays.asList("MS", "Quimica")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "AdR", 4, 5, Arrays.asList("SO", "DDS", "Economia")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "IS", 4, 6, Arrays.asList("ProA", "DDS", "GdD")));
////
//////        //LEVEL 5
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "SG", 5, 2, Arrays.asList("IO", "Sim", "AdR")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "IA", 5, 3, Arrays.asList("IO", "Sim")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "AG", 5, 4, Arrays.asList("AdR", "IO")));
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "Redes", 5, 6, Arrays.asList("Com", "SO")));
////
//////        //LEVEL 6
//        subjects.add(new SubjectView(  getApplication().getBaseContext(), "PF", 6, 5, Arrays.asList("AdR", "IS", "Redes", "Legis")));
//
//    }

}
