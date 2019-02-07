package com.example.lukaspeter.bullshitbingo.helpers;

/**
 *  This class is used in the GameView to save the in game state of a game item.
 */
public class TempItem {

    private int id;
    private String name;
    private boolean checked;

    public TempItem(int id, String name, boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}