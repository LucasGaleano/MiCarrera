package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.lucasgaleano.micarrera.R;

@Database(entities = {Subject.class, SubjectPredecessor.class,
                        Exam.class}, version = 1)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {
    public abstract Dao Dao();

    private static RoomDatabase INSTANCE;

    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "Subjects_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final Dao mDao;


        public PopulateDbAsync(RoomDatabase instance) {
            mDao = instance.Dao();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            mDao.deleteAllPredecessor();
            mDao.deleteAllSubject();

            populateInitialDataBase();


            return null;
        }

        private void populateInitialDataBase() {

            int initialState = 0;
            //LEVEL 1
            mDao.insert(new Subject("Fisica I", initialState, 1, 1));
            mDao.insert(new Subject("Arq", initialState, 1, 2));
            mDao.insert(new Subject("AGA", initialState, 1, 3));
            mDao.insert(new Subject("AM I", initialState, 1, 4));
            mDao.insert(new Subject("SyO", initialState, 1, 5));
            mDao.insert(new Subject("AyED", initialState, 1, 6));
            mDao.insert(new Subject("MD", initialState, 1, 7));
            mDao.insert(new Subject("Ingles I", initialState, 1, 8));
            //mDao.insert(new Subject("Quimica",initialState,1,9));
            //mDao.insert(new Subject("IyS",initialState,1,10));
            //mDao.insert(new Subject("SDR",initialState,1,11));


            //LEVEL 2
            mDao.insert(new Subject("Fisica II", initialState, 2, 1));
            mDao.insert(new Subject("ProA", initialState, 2, 3));
            mDao.insert(new Subject("AM II", initialState, 2, 4));
            mDao.insert(new Subject("AdS", initialState, 2, 5));
            mDao.insert(new Subject("SSL", initialState, 2, 6));
            mDao.insert(new Subject("PdeP", initialState, 2, 7));
            mDao.insert(new Subject("SO", initialState, 2, 8));
            mDao.insert(new Subject("Ingles II", initialState, 2, 9));

            //LEVEL 3
            mDao.insert(new Subject("MS", initialState, 3, 3));
            mDao.insert(new Subject("Economia", initialState, 3, 4));
            mDao.insert(new Subject("DDS", initialState, 3, 5));
            mDao.insert(new Subject("GdD", initialState, 3, 6));
            mDao.insert(new Subject("Legis", initialState, 3, 7));

            //LEVEL 4
            mDao.insert(new Subject("Sim", initialState, 4, 1));
            mDao.insert(new Subject("IO", initialState, 4, 2));
            mDao.insert(new Subject("Com", initialState, 4, 3));
            mDao.insert(new Subject("TdC", initialState, 4, 4));
            mDao.insert(new Subject("AdR", initialState, 4, 5));
            mDao.insert(new Subject("IS", initialState, 4, 6));

            //LEVEL 5
            mDao.insert(new Subject("SG", initialState, 5, 2));
            mDao.insert(new Subject("IA", initialState, 5, 3));
            mDao.insert(new Subject("AG", initialState, 5, 4));
            mDao.insert(new Subject("Redes", initialState, 5, 6));

            //LEVEL 6
            mDao.insert(new Subject("PF", initialState, 6, 5));

            //Predeccesor LEVEL 2
            mDao.insert(new SubjectPredecessor("Fisica II", "AM I"));
            mDao.insert(new SubjectPredecessor("Fisica II", "Fisica I"));
            mDao.insert(new SubjectPredecessor("ProA", "AM I"));
            mDao.insert(new SubjectPredecessor("ProA", "AGA"));
            mDao.insert(new SubjectPredecessor("AM II", "AGA"));
            mDao.insert(new SubjectPredecessor("AM II", "AM I"));
            mDao.insert(new SubjectPredecessor("AdS", "SyO"));
            mDao.insert(new SubjectPredecessor("AdS", "AyED"));
            mDao.insert(new SubjectPredecessor("SSL", "MD"));
            mDao.insert(new SubjectPredecessor("SSL", "AyED"));
            mDao.insert(new SubjectPredecessor("PdeP", "AyED"));
            mDao.insert(new SubjectPredecessor("PdeP", "MD"));
            mDao.insert(new SubjectPredecessor("SO", "MD"));
            mDao.insert(new SubjectPredecessor("SO", "AyED"));
            mDao.insert(new SubjectPredecessor("SO", "Arq"));
            mDao.insert(new SubjectPredecessor("Ingles II", "Ingles I"));

            //Predeccesor LEVEL 3
            mDao.insert(new SubjectPredecessor("MS", "AM II"));
            mDao.insert(new SubjectPredecessor("Economia", "AdS"));
            mDao.insert(new SubjectPredecessor("DDS", "AdS"));
            mDao.insert(new SubjectPredecessor("DDS", "PdeP"));
            mDao.insert(new SubjectPredecessor("GdD", "AdS"));
            mDao.insert(new SubjectPredecessor("GdD", "SSL"));
            mDao.insert(new SubjectPredecessor("GdD", "PdeP"));
            mDao.insert(new SubjectPredecessor("Legis", "AdS"));
            mDao.insert(new SubjectPredecessor("Legis", "IyS"));

            //Predeccesor LEVEL 4
            mDao.insert(new SubjectPredecessor("Sim", "ProA"));
            mDao.insert(new SubjectPredecessor("Sim", "MS"));
            mDao.insert(new SubjectPredecessor("IO", "ProA"));
            mDao.insert(new SubjectPredecessor("IO", "MS"));
            mDao.insert(new SubjectPredecessor("Com", "ProA"));
            mDao.insert(new SubjectPredecessor("Com", "Fisica II"));
            mDao.insert(new SubjectPredecessor("Com", "Arq"));
            mDao.insert(new SubjectPredecessor("TdC", "MS"));
            mDao.insert(new SubjectPredecessor("TdC", "Quimica"));
            mDao.insert(new SubjectPredecessor("AdR", "Economia"));
            mDao.insert(new SubjectPredecessor("AdR", "DDS"));
            mDao.insert(new SubjectPredecessor("AdR", "SO"));
            mDao.insert(new SubjectPredecessor("IS", "ProA"));
            mDao.insert(new SubjectPredecessor("IS", "DDS"));
            mDao.insert(new SubjectPredecessor("IS", "GdD"));

            //Predeccesor LEVEL 5
            mDao.insert(new SubjectPredecessor("SG", "IO"));
            mDao.insert(new SubjectPredecessor("SG", "Sim"));
            mDao.insert(new SubjectPredecessor("SG", "AdR"));
            mDao.insert(new SubjectPredecessor("IA", "IO"));
            mDao.insert(new SubjectPredecessor("IA", "Sim"));
            mDao.insert(new SubjectPredecessor("AG", "AdR"));
            mDao.insert(new SubjectPredecessor("AG", "IO"));
            mDao.insert(new SubjectPredecessor("Redes", "Com"));
            mDao.insert(new SubjectPredecessor("Redes", "SO"));

            //Predeccesor LEVEL 6
            mDao.insert(new SubjectPredecessor("PF", "AdR"));
            mDao.insert(new SubjectPredecessor("PF", "IS"));
            mDao.insert(new SubjectPredecessor("PF", "Redes"));
            mDao.insert(new SubjectPredecessor("PF", "Legis"));
        }
    }
}