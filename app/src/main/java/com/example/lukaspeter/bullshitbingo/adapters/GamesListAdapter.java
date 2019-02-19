package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.Game;

import java.util.List;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.GameViewHolder> {

    private final LayoutInflater mInflater;
    private List<Game> mGames;
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
            final Game g = mGames.get(position);

            // TODO: set Text template name
            holder.templateItemView.setText("Template title");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickGamesListItem(g);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.templateItemView.setText("No Game");
        }
    }

    public void setGames(List<Game> games) {
        mGames = games;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mGames != null ? mGames.size() : 0;
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        private final TextView templateItemView;

        private GameViewHolder(View itemView) {
            super(itemView);
            templateItemView = itemView.findViewById(R.id.textView);
        }
    }

    public interface OnClickGamesListListener {
        void onClickGamesListItem(Game game);
    }
}
