package com.example.lukaspeter.bullshitbingo.db;

import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.ItemDao;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.models.TemplateDao;

public class InsertAsyncTask extends AsyncTask<Object, Void, Long> {
    private TemplateDao templateDao;
    private ItemDao itemDao;

    public InsertAsyncTask(Object dao){

        if (dao instanceof TemplateDao) {
            templateDao = (TemplateDao) dao;
        }
        else if (dao instanceof ItemDao) { // TODO Hier war der Bug du kek.. du hast instanceof Item statt ItemDao gemacht
            itemDao = (ItemDao) dao;
        }
    }

    @Override
    protected Long doInBackground(final Object... params){
        long id = 0;
        if (params[0] instanceof Template) {
            id = templateDao.insertTemplate((Template) params[0]);
        }
        else if (params[0] instanceof Item) {
            itemDao.insertItem((Item) params[0]);
        }

        return id;
    }

}
