package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


@Entity(foreignKeys = @ForeignKey(entity = Template.class,
        parentColumns = "id",
        childColumns = "template",
        //TODO: check onDelete
        onDelete = ForeignKey.CASCADE))

public class Game {

    @ColumnInfo(name = "id")
    private @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "startedDate")
    @NonNull
    private Date started;

    @ColumnInfo(name = "finishedDate")
    private Date finished;

    @ColumnInfo(name = "template")
    @NonNull
    private int template;

    @ColumnInfo(name = "itemPositions")
    @NonNull
    private String itemPositions;

    public Game(@NonNull Date started, @NonNull int template) {
        this.started = started;
        this.template = template;

        // Init Positions
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            positions.add(i + 1);
        }
        Collections.shuffle(positions);
        String itemPositions = "";
        for (int i = 0; i < positions.size(); i++) {
            itemPositions += String.valueOf(positions.get(i)) + ";";
        }
        this.itemPositions = itemPositions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public Date getStarted() {
        return started;
    }

    public void setStarted(@NonNull Date started) {
        this.started = started;
    }

    @NonNull
    public Date getFinished() {
        return finished;
    }

    public void setFinished(@NonNull Date finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished != null;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    @NonNull
    public String getItemPositions() {
        return itemPositions;
    }

    public void setItemPositions(@NonNull String itemPositions) {
        this.itemPositions = itemPositions;
    }
}
