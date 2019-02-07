package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.ItemDao;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class InsertAsyncTask extends AsyncTask<Object, Void, Void> {
    private TemplateDao templateDao;
    private ItemDao itemDao;

    public InsertAsyncTask(Object dao){

        if (dao instanceof TemplateDao) {
            templateDao = (TemplateDao) dao;
        }
        else if (dao instanceof Item) {
            itemDao = (ItemDao) dao;
        }
    }

    @Override
    protected Void doInBackground(final Object... params){

        if (params[0] instanceof Template) {
            templateDao.insertTemplate((Template) params[0]);
        }
        else if (params[0] instanceof Item) {
            itemDao.insertItem((Item) params[0]);
        }
        return null;
    }

}
