package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;


@Entity(foreignKeys = @ForeignKey(entity = Template.class,
        parentColumns = "id",
        childColumns = "template",
        //TODO: check onDelete
        onDelete = ForeignKey.CASCADE))

public class Game {
    @ColumnInfo(name = "id")
    private @PrimaryKey(autoGenerate = true) int id;

    @ColumnInfo(name = "startedDate")
    @NonNull
    private Date started;

    @ColumnInfo(name = "finishedDate")
    @NonNull
    private Date finished;

    @ColumnInfo(name = "template")
    @NonNull
    private int template;

    //TODO: Store position of the items in the game
}
