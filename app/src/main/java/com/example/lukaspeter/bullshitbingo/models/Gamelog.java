package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Game.class,
        parentColumns = "id",
        childColumns = "game",
        //TODO: check onDelete
        onDelete = ForeignKey.CASCADE), primaryKeys = {"checked", "game", "item"})
public class Gamelog {
    @ColumnInfo(name = "game")
    private int game;

    @ColumnInfo(name = "item")
    private int item;

    @ColumnInfo(name = "checked")
    private Date checked;
    //TODO log if check was set or deleted?
}
