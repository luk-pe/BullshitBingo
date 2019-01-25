package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

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
    //@NonNull is required but I don't know why
    @NonNull
    private Date checked;
    //TODO log if check was set or deleted?


    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public Date getChecked() {
        return checked;
    }

    public void setChecked(Date checked) {
        this.checked = checked;
    }
}
