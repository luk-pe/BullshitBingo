package com.example.lukaspeter.bullshitbingo.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DataRepository {
    private TemplateDao mTemplateDao;
    private ItemDao mItemDao;
    private LiveData<List<Template>> mAllTemplates;
    private LiveData<List<Item>> mTemplateItems;

    public DataRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mTemplateDao = db.templateDao();
        mItemDao = db.itemDao();
        mAllTemplates = mTemplateDao.getAllTemplates();
    }

    public LiveData<List<Template>> getAllTemplates(){
        return mAllTemplates;
    }
    public LiveData<List<Item>> getTemplateItems(int templateId){
        mItemDao.getTemplateItems(templateId);
        return mTemplateItems;
    }
    public void insertTemplate (Template template){
        new insertAsyncTask(mTemplateDao).execute(template);
    }

    private static class insertAsyncTask extends AsyncTask <Template, Void, Void> {
        private TemplateDao mAsyncTaskTemplateDao;
        insertAsyncTask(TemplateDao dao){
            mAsyncTaskTemplateDao = dao;
        }


        @Override
        protected Void doInBackground(final Template... params){
            mAsyncTaskTemplateDao.insertTemplate(params [0]);
            return null;
        }

    }
}
