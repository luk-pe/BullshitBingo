package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.Template;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;
    private LiveData<List<Template>> mAllTemplates;

    public TemplateViewModel(Application application){
        super(application);
        mDataRepository = new DataRepository(application);
        mAllTemplates = mDataRepository.getAllTemplates();
    }

    public Template getTemplateById(int id) { return mDataRepository.getTemplateById(id); }

    public LiveData<List<Template>> getAllTemplates(){
        return mAllTemplates;
    }

    public void insertTemplate(Template template){
        mDataRepository.insertTemplate(template);
    }
}
