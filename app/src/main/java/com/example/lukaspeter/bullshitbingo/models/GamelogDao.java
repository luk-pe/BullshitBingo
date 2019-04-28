package com.example.lukaspeter.bullshitbingo.models;


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
    List<Gamelog> getGameStatus(int game);

    @Query("SELECT checked FROM Gamelog WHERE item = :item AND game = :game GROUP BY item ORDER BY date DESC")
    boolean getItemStatus(int game, int item);
}
