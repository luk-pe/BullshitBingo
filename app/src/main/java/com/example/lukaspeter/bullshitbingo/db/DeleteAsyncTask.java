package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.AppDatabase;
import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameDao;

public class DeleteAsyncTask extends AsyncTask<Object, Void, Void> {
    private GameDao gameDao;
    private AppDatabase db;

    public DeleteAsyncTask(Object object) {
        if (object instanceof GameDao) {
            gameDao = (GameDao) object;
        } else if (object instanceof AppDatabase){
            db = (AppDatabase) object;
        }
    }

    @Override
    protected Void doInBackground(final Object... params) {
        if (params[0] instanceof Game) {
            gameDao.deleteGame((Game) params[0]);
        } else {
            db.clearAllTables();
        }
        return null;
    }

}
