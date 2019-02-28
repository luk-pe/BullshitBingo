package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.GameActivity;
import com.example.lukaspeter.bullshitbingo.adapters.GamesListAdapter;
import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameWithTemplate;
import com.example.lukaspeter.bullshitbingo.viewModels.GameViewModel;

import java.util.List;

public class MyGamesFragment extends Fragment implements GamesListAdapter.OnClickGamesListListener {

    public static MyGamesFragment newInstance() {
        return new MyGamesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_games, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = this.getActivity().findViewById(R.id.fragment_my_games_recyclerview);
        final GamesListAdapter adapter = new GamesListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // TODO: Change to GameViewModel and pass games to adapter

        GameViewModel mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mGameViewModel.getAllGamesWithTemplate().observe(this, new Observer<List<GameWithTemplate>>() {
            @Override
            public void onChanged(@Nullable List<GameWithTemplate> games) {
                // Update the cached copy of the games in the adapter.
                adapter.setGames(games);
            }
        });

    }

    @Override
    public void onClickGamesListItem(Game game) {

        if (!game.isFinished()) {
            Intent mIntent = new Intent(this.getActivity(), GameActivity.class);
            mIntent.putExtra("game_id", game.getId());
            startActivity(mIntent);
        } else {
            // TODO: Intent for Game stats
        }
    }
}