package com.example.lukaspeter.bullshitbingo.models;

import android.support.annotation.NonNull;
import java.util.Date;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Template {
    @ColumnInfo(name = "id")
    private @PrimaryKey (autoGenerate = true) int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "creator")
    private String creator;

    @ColumnInfo(name = "priv")
    private boolean priv = true;

    @ColumnInfo(name = "createdDate")
    @NonNull
    private Date created;
}
