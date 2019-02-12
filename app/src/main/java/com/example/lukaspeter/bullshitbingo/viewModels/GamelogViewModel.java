package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.Gamelog;

import java.util.List;

public class GamelogViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    public GamelogViewModel(Application application) {
        super(application);
        mDataRepository = new DataRepository(application);
    }

    public void insertGamelog(Gamelog gamelog) {
        mDataRepository.insertGamelog(gamelog);
    }

    public LiveData<List<Gamelog>> getGameStatus(int gameId) {
        LiveData<List<Gamelog>> mGamelogEntries = mDataRepository.getGameStatus(gameId);
        return mGamelogEntries;
    }
}
