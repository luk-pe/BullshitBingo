package com.example.lukaspeter.bullshitbingo.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameWithTemplate;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    public GameViewModel(Application application) {
        super(application);
        mDataRepository = new DataRepository(application);
    }

    public long insertGame(Game game) {
        long id = mDataRepository.insertGame(game);
        return id;
    }

    public void updateGame(Game game) {
        mDataRepository.updateGame(game);
    }

    public void deleteGame(Game game) {
        mDataRepository.deleteGame(game);
    }

    public Game getGameById(int id) {
        return mDataRepository.getGameById(id);
    }

    public LiveData<List<Game>> getAllGames(){
        return mDataRepository.getAllGames();
    }

    public LiveData<List<GameWithTemplate>> getAllGamesWithTemplate(){
        return mDataRepository.getAllGamesWithTemplate();
    }
}
