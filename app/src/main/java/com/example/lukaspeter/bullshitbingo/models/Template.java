package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;


@Entity
public class Template {
    @ColumnInfo(name = "id")
    private @PrimaryKey(autoGenerate = true) int id;

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
