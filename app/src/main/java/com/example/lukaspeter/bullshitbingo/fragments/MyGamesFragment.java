package com.example.lukaspeter.bullshitbingo.fragments;

import android.content.Intent;
import android.os.Bundle;
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

public class MyGamesFragment extends Fragment implements GamesListAdapter.OnClickGamesListListener {

    public static MyGamesFragment newInstance() {
        MyGamesFragment fragment = new MyGamesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_games, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = this.getActivity().findViewById(R.id.fragment_my_games_recyclerview);
        final GamesListAdapter adapter = new GamesListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // TODO: Change to GameViewModel and pass games to adapter
        /*
        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mTemplateViewModel.getAllTemplates().observe(this, new Observer<List<Template>>() {
            @Override
            public void onChanged(@Nullable List<Template> templates) {
                // Update the cached copy of the templates in the adapter.
                adapter.setTemplates(templates);
            }
        });
        */
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