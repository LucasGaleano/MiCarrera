package com.example.lucasgaleano.buttonexample.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

@Database(entities = {Subject.class, SubjectPredecessor.class}, version = 1)
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
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
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

            mDao.deleteAll();

            mDao.insert(new Subject("Fisica I", 0, 1, 1));
            mDao.insert(new Subject("Arq", 1, 2, 2));
            mDao.insert(new Subject("AGA", 1, 3, 3));
            mDao.insert(new Subject("AM I", 1, 4, 4));




            return null;
        }
    }
}
