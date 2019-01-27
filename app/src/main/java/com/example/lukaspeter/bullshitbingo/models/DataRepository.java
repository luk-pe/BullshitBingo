package com.example.lukaspeter.bullshitbingo.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DataRepository {
    private TemplateDao mTemplateDao;
    private LiveData<List<Template>> mAllTemplates;

    // TODO: confirm if public is okay here-> different to tutorial -> asl michi
    public DataRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mTemplateDao = db.templateDao();
        mAllTemplates = mTemplateDao.getAllTemplates();
    }
    // TODO: confirm if public is okay here-> different to tutorial -> asl michi
    public LiveData<List<Template>> getAllTemplates(){
        return mAllTemplates;
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
            mAsyncTaskTemplateDao.insertTemplates(params [0]);
            return null;
        }

    }
}
