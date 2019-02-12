package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    public ItemViewModel(Application application){
        super(application);
        mDataRepository = new DataRepository(application);
    }
    public LiveData<List<Item>> getTemplateItems(int templateId){
        LiveData<List<Item>> mTemplateItems = mDataRepository.getTemplateItems(templateId);
        return mTemplateItems;
    }
    public void insertItem(Item item){
        mDataRepository.insertItem(item);
    }
}
