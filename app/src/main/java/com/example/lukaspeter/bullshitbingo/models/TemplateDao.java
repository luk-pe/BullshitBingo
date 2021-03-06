package com.example.lukaspeter.bullshitbingo.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TemplateDao {

    @Insert(onConflict = IGNORE)
    long insertTemplate(Template template);

    @Delete
    void deleteTemplate(Template template);

    @Query("DELETE FROM Template")
    void deleteAllTemplates();

    @Query("SELECT * FROM Template")
    LiveData<List<Template>> getAllTemplates();

    @Query("SELECT * FROM Template WHERE id= :id")
    Template templateById(int id);

    @Query("SELECT * FROM Template WHERE remoteId= :remoteId")
    Template templateByRemoteId(String remoteId);

    @Query("SELECT * FROM Template WHERE name LIKE :input OR creator LIKE :input")
    List<Template> findTemplate(String input);

    @Query("UPDATE Template SET description = :description WHERE id= :id")
    void updateDescription(String description, int id);


}
