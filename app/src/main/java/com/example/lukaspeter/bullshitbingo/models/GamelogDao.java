package com.example.lukaspeter.bullshitbingo.models;

import androidx.room.Dao;
import androidx.room.Insert;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface GamelogDao {

    //TODO: check onConflict
    @Insert(onConflict = IGNORE)
    void insertGamelog(Gamelog ... gamelogs);
}
