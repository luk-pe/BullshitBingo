package com.example.lukaspeter.bullshitbingo.models;

import android.support.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(foreignKeys = @ForeignKey(entity = Template.class,
        parentColumns = "id",
        childColumns = "template",
        //TODO: check onDelete
        onDelete = ForeignKey.CASCADE), primaryKeys = {"id", "template"})

public class Item {
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "template")
    @NonNull
    private int template;

}
