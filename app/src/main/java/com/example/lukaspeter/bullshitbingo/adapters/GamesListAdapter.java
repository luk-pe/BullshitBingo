package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.GameWithTemplate;

import java.util.List;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.GameViewHolder> {

    private final LayoutInflater mInflater;
    private List<GameWithTemplate> mGames;
    private OnClickGamesListListener mListener;

    public GamesListAdapter(Context context, OnClickGamesListListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View gameView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new GameViewHolder(gameView);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        if (mGames != null) {
            final GameWithTemplate g = mGames.get(position);
            holder.templateItemView.setText(g.getTemplate().getName());
            holder.gameItemStartedView.setText(g.getGame().getStarted().toString());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickGamesListItem(g.getGame());
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.templateItemView.setText("No Game");
        }
    }

    public void setGames(List<GameWithTemplate> games) {
        mGames = games;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mGames != null ? mGames.size() : 0;
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        private final TextView templateItemView;
        private final TextView gameItemStartedView;

        private GameViewHolder(View itemView) {
            super(itemView);
            templateItemView = itemView.findViewById(R.id.textView);
            gameItemStartedView = itemView.findViewById(R.id.textView2);

        }
    }

    public interface OnClickGamesListListener {
        void onClickGamesListItem(Game game);
    }
}
