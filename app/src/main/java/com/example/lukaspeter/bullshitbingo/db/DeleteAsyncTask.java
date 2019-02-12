package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameDao;

public class DeleteAsyncTask extends AsyncTask<Object, Void, Void> {
    private GameDao gameDao;

    public DeleteAsyncTask(Object dao) {
        if (dao instanceof GameDao) {
            gameDao = (GameDao) dao;
        }
    }

    @Override
    protected Void doInBackground(final Object... params) {
        if (params[0] instanceof Game) {
            gameDao.deleteGame((Game) params[0]);
        }
        return null;
    }

}
