package com.example.lukaspeter.bullshitbingo.models;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface GamelogDao {

    //TODO: check onConflict
    @Insert(onConflict = IGNORE)
    void insertGamelog(Gamelog gamelog);

    @Query("SELECT * FROM Gamelog WHERE game = :game GROUP BY item ORDER BY date DESC")
    LiveData<List<Gamelog>> getGameStatus(int game);
}
