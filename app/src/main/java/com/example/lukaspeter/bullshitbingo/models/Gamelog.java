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
        onDelete = ForeignKey.CASCADE), primaryKeys = {"date", "game", "item"})

public class Gamelog {
    @ColumnInfo(name = "game")
    private int game;

    @ColumnInfo(name = "item")
    private int item;

    @ColumnInfo(name = "date")
    //@NonNull is required here but I don't know why
    @NonNull
    private Date date;

    @ColumnInfo(name = "checked")
    private boolean checked;


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

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
