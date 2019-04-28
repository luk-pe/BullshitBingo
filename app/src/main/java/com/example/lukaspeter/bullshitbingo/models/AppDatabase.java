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

@Database(entities = {Game.class, Gamelog.class, Item.class, Template.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GameDao gameDao();

    public abstract GamelogDao gamelogDao();

    public abstract ItemDao itemDao();

    public abstract TemplateDao templateDao();

    private static volatile AppDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bullshit-bingo-database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    // Class to generate test data
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TemplateDao templateDao;
        private final ItemDao itemDao;

        PopulateDbAsync(AppDatabase db) {
            templateDao = db.templateDao();
            itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            // Create test data if needed...

            return null;
        }
    }
}
