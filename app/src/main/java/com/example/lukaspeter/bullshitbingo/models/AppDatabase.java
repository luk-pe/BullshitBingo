package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.lukaspeter.bullshitbingo.helpers.Converters;

@Database(entities = {Game.class, Gamelog.class, Item.class, Template.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
    public abstract GamelogDao gamelogDao();
    public abstract ItemDao itemDao();
    public abstract TemplateDao templateDao();
}
