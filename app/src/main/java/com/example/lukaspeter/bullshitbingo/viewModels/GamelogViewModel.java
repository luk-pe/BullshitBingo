package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

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

    public List<Gamelog> getGameStatus(int gameId) {
        List<Gamelog> mGamelogEntries = mDataRepository.getGameStatus(gameId);
        return mGamelogEntries;
    }
    public boolean getItemStatus(int gameId, int itemId) {
        boolean mChecked = mDataRepository.getItemStatus(gameId, itemId);
        return mChecked;
    }
}
