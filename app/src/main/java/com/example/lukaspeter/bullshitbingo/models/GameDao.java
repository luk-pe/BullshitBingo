package com.example.lukaspeter.bullshitbingo.models;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game WHERE finishedDate IS NULL")
    List<Game> openGames();

    //TODO: check onConflict
    @Insert(onConflict = REPLACE)
    void insertGames(Game ... games);

    @Update
    void updateGames(Game ... games);

    @Delete
    void deleteGames(Game ... games);
}
