package com.example.lukaspeter.bullshitbingo.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ItemDao {

    @Insert(onConflict = REPLACE)
    void insertItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM Item")
    void deleteAllItems();

    @Query("SELECT * FROM Item WHERE template= :template")
    LiveData<List<Item>> getTemplateItems(int template);
}
