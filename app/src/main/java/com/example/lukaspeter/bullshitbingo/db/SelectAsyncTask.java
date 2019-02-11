package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.ItemDao;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class SelectAsyncTask extends AsyncTask<Integer, Void, Object> {
    private TemplateDao templateDao;
    private ItemDao itemDao;

    public SelectAsyncTask(Object dao){

        if (dao instanceof TemplateDao) {
            templateDao = (TemplateDao) dao;
        }
        else if (dao instanceof ItemDao) {
            itemDao = (ItemDao) dao;
        }
    }

    @Override
    protected Object doInBackground(final Integer... params){

        if (templateDao != null) {
            if (params[0] != 0) return templateDao.templateById( params[0]);
            else return templateDao.getAllTemplates();
        } else {
            return null;
        }
    }
}
