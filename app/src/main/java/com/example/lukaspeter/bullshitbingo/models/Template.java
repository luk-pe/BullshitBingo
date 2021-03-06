package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;


@Entity
public class Template {
    @ColumnInfo(name = "id")

    private @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "creator")
    private String creator;

    @ColumnInfo(name = "priv")
    private boolean priv = true;

    @ColumnInfo(name = "created")
    @NonNull
    private Date created;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "remoteId")
    private String remoteId;

    public Template(@NonNull String name, String creator, boolean priv, @NonNull Date created, String description, String remoteId) {
        this.name = name;
        this.id = id;
        this.creator = creator;
        this.priv = priv;
        this.created = created;
        this.description = description;
        this.remoteId = remoteId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isPriv() {
        return priv;
    }

    public void setPriv(boolean priv) {
        this.priv = priv;
    }

    @NonNull
    public Date getCreated() {
        return created;
    }

    public void setCreated(@NonNull Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }
}
