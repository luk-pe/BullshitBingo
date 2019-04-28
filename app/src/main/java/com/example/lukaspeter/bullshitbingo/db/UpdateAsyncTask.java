package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameDao;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class UpdateAsyncTask extends AsyncTask<Object, Void, Void> {
    private GameDao gameDao;
    private TemplateDao templateDao;

    public UpdateAsyncTask(Object dao) {
        if (dao instanceof GameDao) {
            gameDao = (GameDao) dao;
        } else if (dao instanceof TemplateDao) {
            templateDao = (TemplateDao) dao;
        }
    }

    @Override
    protected Void doInBackground(final Object... params) {
        if (gameDao != null) {
            if (params[0] instanceof Game) {
                gameDao.updateGame((Game) params[0]);
            }
        } else if (templateDao != null) {
            if (params[0] instanceof String && params[1] instanceof Integer) {
                templateDao.updateDescription((String) params[0], (Integer) params[1]);
            }
        }
        return null;
    }

}
