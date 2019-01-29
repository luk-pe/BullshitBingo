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

    public void insertItem (Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask <Object, Void, Void> {
        private TemplateDao templateDao;
        private ItemDao itemDao;

        public insertAsyncTask(Object dao){

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
}
