package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.helpers.TempItem;

import java.util.List;

public class GameGridViewAdapter extends BaseAdapter {

    private List<TempItem> items;
    private Context mContext;
    private OnClickGridViewItemListener mListener;

    public GameGridViewAdapter(List<TempItem> items, Context mContext, OnClickGridViewItemListener listener) {
        this.items = items;
        this.mContext = mContext;
        this.mListener = listener;
    }

    public void setItems(List<TempItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.gridview_game_item, viewGroup, false);
        }

        final TempItem item = items.get(i);

        TextView textViewTitle = view.findViewById(R.id.gridview_game_item_title);
        textViewTitle.setText(item.getName());

        // Adjust Font size
        if (item.getName().length() > 50) textViewTitle.setTextSize(8);
        else if (item.getName().length() > 30) textViewTitle.setTextSize(12);
        else if (item.getName().length() > 10) textViewTitle.setTextSize(14);
        else textViewTitle.setTextSize(18);

        int color = item.isChecked() ? mContext.getResources().getColor(R.color.colorPrimary) : Color.WHITE;
        view.setBackgroundColor(color);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickGridViewItem(item);
                notifyDataSetChanged();
            }
        });


        return view;
    }


    public interface OnClickGridViewItemListener {

        void onClickGridViewItem(TempItem item);
    }

}
