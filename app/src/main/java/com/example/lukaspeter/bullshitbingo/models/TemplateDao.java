package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TemplateDao {

    //TODO: check onConflict
    @Insert(onConflict = IGNORE)
    void insertTemplates (Template ... templates);

    @Delete
    void deleteTemplates (Template ... templates);

    @Query("SELECT * FROM Template WHERE id= :id")
    Template templateById(int id);

    @Query("SELECT * FROM Template WHERE name LIKE :input OR creator LIKE :input")
    List<Template> findTemplate(String input);
    //TODO: add "%" to the input

}
