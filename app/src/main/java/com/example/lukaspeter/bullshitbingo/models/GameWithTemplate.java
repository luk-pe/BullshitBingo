package com.example.lukaspeter.bullshitbingo.models;

import android.arch.persistence.room.Embedded;

public class GameWithTemplate {
    @Embedded
    Game game;
    @Embedded(prefix = "template_")
    Template template;

    public Game getGame() {
        return game;
    }

    public Template getTemplate() {
        return template;
    }
}
