package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.GameDao;
import com.example.lukaspeter.bullshitbingo.models.GamelogDao;
import com.example.lukaspeter.bullshitbingo.models.ItemDao;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class SelectAsyncTask extends AsyncTask<Integer, Void, Object> {
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
    protected Object doInBackground(final Integer... params) {

        if (templateDao != null) {
            if (params[0] != 0) return templateDao.templateById(params[0]);
            else return templateDao.getAllTemplates();
        } else if (itemDao != null) {
            return itemDao.getTemplateItems(params[0]);
        } else if (gameDao != null) {
            if (params[0] != 0) return gameDao.gameById(params[0]);
            else return gameDao.getAllGames();
        } else if (gamelogDao != null) {
            if(params[1] != 0) return gamelogDao.getItemStatus(params[0], params[1]);
            else return gamelogDao.getGameStatus(params[0]);
        }else {
            return null;
        }
    }
}
