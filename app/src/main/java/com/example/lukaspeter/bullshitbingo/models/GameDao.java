package com.example.lukaspeter.bullshitbingo.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface GameDao {

    @Query("SELECT * FROM game WHERE finishedDate IS NULL")
    List<Game> openGames();

    //TODO: check onConflict
    @Insert(onConflict = REPLACE)
    long insertGame(Game game);

    @Update
    void updateGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Query("SELECT * FROM game WHERE id = :id")
    Game gameById(int id);

    @Query("SELECT Game.*," +
            "Template.id AS template_id, Template.name as template_name, Template.created as template_created, Template.creator as template_creator, Template.priv as template_priv " +
            "FROM Game INNER JOIN Template ON Game.template = Template.id")
    LiveData<List<GameWithTemplate>> getAllGamesWithTemplate();

    // ToDo: delete method
    @Query("SELECT * FROM game")
    LiveData<List<Game>> getAllGames();


}
