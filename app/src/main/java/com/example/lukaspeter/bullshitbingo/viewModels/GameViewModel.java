package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.Game;

public class GameViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    public GameViewModel (Application application){
        super(application);
        mDataRepository = new DataRepository(application);
    }

    public void insertGame(Game game){
        mDataRepository.insertGame(game);
    }

    public void updateGame(Game game){
        mDataRepository.updateGame(game);
    }

    public void deleteGame(Game game){
        mDataRepository.deleteGame(game);
    }
}
