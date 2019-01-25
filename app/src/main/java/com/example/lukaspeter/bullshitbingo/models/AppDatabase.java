package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Game.class, Gamelog.class, Item.class, Template.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
    public abstract GamelogDao gamelogDao();
    public abstract ItemDao itemDao();
    public abstract TemplateDao templateDao();
}
