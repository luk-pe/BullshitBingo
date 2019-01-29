package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.lukaspeter.bullshitbingo.helpers.Converters;

import java.util.Date;

@Database(entities = {Game.class, Gamelog.class, Item.class, Template.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
    public abstract GamelogDao gamelogDao();
    public abstract ItemDao itemDao();
    public abstract TemplateDao templateDao();

    private static volatile AppDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    static AppDatabase getDatabase(final Context context){
        if (INSTANCE ==  null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "bullshit-bingo-database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TemplateDao mDao;
        PopulateDbAsync(AppDatabase db) {
            mDao = db.templateDao();
        }
        @Override
        protected Void doInBackground(final Void... params){
            //mDao.deleteAllTemplates();
            Template template = new Template("Dinge die Michi sagt", "Lukas", false, new Date());
            mDao.insertTemplate(template);
            template = new Template("Autos die von Lüdin gerammt werden", "Lukas", false, new Date());
            mDao.insertTemplate(template);
            return null;
        }
    }
}
