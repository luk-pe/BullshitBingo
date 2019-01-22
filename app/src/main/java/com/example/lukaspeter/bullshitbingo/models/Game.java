package com.example.lukaspeter.bullshitbingo.models;

import android.support.annotation.NonNull;
import java.util.Date;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity (foreignKeys = @ForeignKey(entity = Template.class,
        parentColumns = "id",
        childColumns = "template",
        //TODO: check onDelete
        onDelete = ForeignKey.CASCADE))

public class Game {
    @ColumnInfo(name = "id")
    private @PrimaryKey (autoGenerate = true) int id;

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
