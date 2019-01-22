package com.example.lukaspeter.bullshitbingo.models;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

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
