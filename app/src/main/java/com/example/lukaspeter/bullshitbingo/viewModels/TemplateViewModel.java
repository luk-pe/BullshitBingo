package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;
import com.example.lukaspeter.bullshitbingo.models.Template;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;
    private LiveData<List<Template>> mAllTemplates;

    public TemplateViewModel(Application application) {
        super(application);
        mDataRepository = new DataRepository(application);
        mAllTemplates = mDataRepository.getAllTemplates();
    }

    public Template getTemplateById(int id) {
        return mDataRepository.getTemplateById(id);
    }

    public LiveData<List<Template>> getAllTemplates() {
        return mAllTemplates;
    }

    public LiveData<List<RemoteTemplate>> getAllRemoteTemplates() {
        return mDataRepository.getAllRemoteTemplates();
    }

    public LiveData<List<RemoteTemplate>> findRemoteTemplatesByName(String name) {
        return mDataRepository.findRemoteTemplatesByName(name);
    }

    public long insertTemplate(Template template) {
        long id = mDataRepository.insertTemplate(template);
        return id;
    }

    public LiveData<Boolean> uploadTemplate(Template template, List<Item> items, String description) {
        return mDataRepository.uploadTemplate(template,items,description);
    }
}
