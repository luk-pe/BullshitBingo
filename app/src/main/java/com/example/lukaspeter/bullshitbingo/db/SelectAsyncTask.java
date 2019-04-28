package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.GameDao;
import com.example.lukaspeter.bullshitbingo.models.GamelogDao;
import com.example.lukaspeter.bullshitbingo.models.ItemDao;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class SelectAsyncTask extends AsyncTask<Object, Void, Object> {
    private TemplateDao templateDao;
    private ItemDao itemDao;
    private GameDao gameDao;
    private GamelogDao gamelogDao;

    public SelectAsyncTask(Object dao) {

        if (dao instanceof TemplateDao) {
            templateDao = (TemplateDao) dao;
        } else if (dao instanceof ItemDao) {
            itemDao = (ItemDao) dao;
        } else if (dao instanceof GameDao) {
            gameDao = (GameDao) dao;
        } else if (dao instanceof GamelogDao) {
            gamelogDao = (GamelogDao) dao;
        }
    }

    @Override
    protected Object doInBackground(final Object... params) {

        if (templateDao != null) {
            if (params[0] instanceof Integer && (Integer) params[0] > 0) {
                return templateDao.templateById((Integer) params[0]);
            } else if (params[0] instanceof String) {
                return templateDao.templateByRemoteId((String) params[0]);
            } else {
                return templateDao.getAllTemplates();
            }
        } else if (itemDao != null) {
            return itemDao.getTemplateItems((Integer) params[0]);
        } else if (gameDao != null) {
            if ((Integer) params[0] != 0) return gameDao.gameById((Integer) params[0]);
            else return gameDao.getAllGamesWithTemplate();
        } else if (gamelogDao != null) {
            if (params[1] != null)
                return gamelogDao.getItemStatus((Integer) params[0], (Integer) params[1]);
            else return gamelogDao.getGameStatus((Integer) params[0]);
        } else {
            return null;
        }
    }
}
