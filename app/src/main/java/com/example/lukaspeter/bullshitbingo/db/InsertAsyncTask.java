package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameDao;
import com.example.lukaspeter.bullshitbingo.models.Gamelog;
import com.example.lukaspeter.bullshitbingo.models.GamelogDao;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.ItemDao;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class InsertAsyncTask extends AsyncTask<Object, Void, Long> {
    private TemplateDao templateDao;
    private ItemDao itemDao;
    private GameDao gameDao;
    private GamelogDao gamelogDao;

    public InsertAsyncTask(Object dao) {

        if (dao instanceof TemplateDao) {
            templateDao = (TemplateDao) dao;
        } else if (dao instanceof ItemDao) { // TODO Hier war der Bug du kek.. du hast instanceof Item statt ItemDao gemacht
            //TODO es war dein bug du kek
            itemDao = (ItemDao) dao;
        } else if (dao instanceof GameDao) {
            gameDao = (GameDao) dao;
        } else if (dao instanceof GamelogDao) {
            gamelogDao = (GamelogDao) dao;
        }
    }

    @Override
    protected Long doInBackground(final Object... params) {
        long id = 0;
        if (params[0] instanceof Template) {
            id = templateDao.insertTemplate((Template) params[0]);
        } else if (params[0] instanceof Item) {
            itemDao.insertItem((Item) params[0]);
        } else if (params[0] instanceof Game) {
            gameDao.insertGame((Game) params[0]);
        } else if (params[0] instanceof Gamelog) {
            gamelogDao.insertGamelog((Gamelog) params[0]);
        }

        return id;
    }

}
