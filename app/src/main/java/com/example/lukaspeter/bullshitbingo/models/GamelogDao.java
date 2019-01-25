package com.example.lukaspeter.bullshitbingo.models;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface GamelogDao {

    //TODO: check onConflict
    @Insert(onConflict = IGNORE)
    void insertGamelog(Gamelog ... gamelogs);
}
