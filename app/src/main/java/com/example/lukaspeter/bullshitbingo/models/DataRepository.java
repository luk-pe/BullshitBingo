package com.example.lukaspeter.bullshitbingo.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lukaspeter.bullshitbingo.db.InsertAsyncTask;
import com.example.lukaspeter.bullshitbingo.db.SelectAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataRepository {
    private TemplateDao mTemplateDao;
    private ItemDao mItemDao;
    private LiveData<List<Item>> mTemplateItems;

    public DataRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mTemplateDao = db.templateDao();
        mItemDao = db.itemDao();
    }

    /**
     *      TEMPLATE METHODS
     */

    public Template getTemplateById(int id) {
        try {
            return (Template) new SelectAsyncTask(mTemplateDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LiveData<List<Template>> getAllTemplates(){
        try {
            return (LiveData<List<Template>>) new SelectAsyncTask(mTemplateDao).execute(0).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    public LiveData<List<Item>> getTemplateItems(int templateId){
        mTemplateItems = mItemDao.getTemplateItems(templateId);
        return mTemplateItems;
    }

    public void insertTemplate (Template template){
        new InsertAsyncTask(mTemplateDao).execute(template);
    }

    /**
     *      ITEM METHODS
     */

    public void insertItem (Item item) {
        new InsertAsyncTask(mItemDao).execute(item);
    }
}
