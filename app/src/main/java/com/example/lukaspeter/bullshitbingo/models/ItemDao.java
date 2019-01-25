package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ItemDao {

    //TODO: check onConflict
    @Insert(onConflict = REPLACE)
    void insertItems(Item ... items);

    @Delete
    void deleteItems(Item ... items);

    @Query("SELECT * FROM Item WHERE template= :template")
    List<Item> templateItems(int template);
}
