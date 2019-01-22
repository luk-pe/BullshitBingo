package com.example.lukaspeter.bullshitbingo.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface TemplateDao {

    //TODO: check onConflict
    @Insert(onConflict = IGNORE)
    void insertTemplates (Template ... templates);

    @Delete
    void deleteTemplates (Template ... templates);

    @Query("SELECT * FROM Template WHERE id= :id")
    void templateById(int id);

    @Query("SELECT * FROM Template WHERE name LIKE :input OR creator LIKE :input")
    void findTemplate(String input);
    //TODO: add "%" to the input

}
