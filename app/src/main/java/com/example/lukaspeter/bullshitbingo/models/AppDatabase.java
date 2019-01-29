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
    //Create Test Data
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TemplateDao templateDao;
        private final ItemDao itemDao;
        PopulateDbAsync(AppDatabase db) {
            templateDao = db.templateDao();
            itemDao=db.itemDao();
        }
        @Override
        protected Void doInBackground(final Void... params){
            //templateDao.deleteAllTemplates();
            itemDao.deleteAllItems();
            Template template = new Template("Dinge die Michi sagt", "Lukas", false, new Date());
            templateDao.insertTemplate(template);
            template = new Template("Autos die von Lüdin gerammt werden", "Lukas", false, new Date());
            templateDao.insertTemplate(template);
            Item item = new Item(1, "Du hast die Kontrolle über dein Leben verloren", 1);
            itemDao.insertItem(item);
            item = new Item(2, "Lukas ist der Beste", 1);
            itemDao.insertItem(item);
            item = new Item(3, "kek", 1);
            itemDao.insertItem(item);
            item = new Item(4, "ober anstrengend", 1);
            itemDao.insertItem(item);
            item = new Item(5, "Beste", 1);
            itemDao.insertItem(item);
            item = new Item(6, "React ist über einfach", 1);
            itemDao.insertItem(item);
            item = new Item(7, "über lässig", 1);
            itemDao.insertItem(item);
            item = new Item(8, "edel", 1);
            itemDao.insertItem(item);
            item = new Item(9, "Bild+", 1);
            itemDao.insertItem(item);
            item = new Item(10, "Junge", 1);
            itemDao.insertItem(item);
            item = new Item(11, "ich war in Frankreich einkaufen", 1);
            itemDao.insertItem(item);
            item = new Item(12, "Cola Zero", 1);
            itemDao.insertItem(item);
            item = new Item(13, "Wenn ich Euro Jackpot gewinn", 1);
            itemDao.insertItem(item);
            item = new Item(14, "Kein Bock auf Praxisphase bei Coop", 1);
            itemDao.insertItem(item);
            item = new Item(15, "Ich geh in Europapark", 1);
            itemDao.insertItem(item);
            item = new Item(16, "Macan", 1);
            itemDao.insertItem(item);


            return null;
        }
    }
}
