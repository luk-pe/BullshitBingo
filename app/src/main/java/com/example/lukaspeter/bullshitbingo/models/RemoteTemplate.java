package com.example.lukaspeter.bullshitbingo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RemoteTemplate implements Serializable {

    private String id;
    private String name;
    private String creator;
    private String description;
    private long downloaded;
    private Date created;
    private ArrayList<String> items;

    public RemoteTemplate(String id, String name, String creator, String description, long downloaded, Date created, ArrayList<String> items) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.downloaded = downloaded;
        this.created = created;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloadCounter) {
        this.downloaded = downloadCounter;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
