package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;

import java.util.HashMap;
import java.util.List;

public class SubscribesToAdapter extends RecyclerView.Adapter<SubscribesToAdapter.SubscribesToListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<HashMap<String, String>> mUsers;

    public SubscribesToAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public SubscribesToListItemViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View templateView = mInflater.inflate(R.layout.search_list_item, parent, false);
        return new SubscribesToAdapter.SubscribesToListItemViewHolder(templateView);
    }

    @Override
    public void onBindViewHolder(SubscribesToAdapter.SubscribesToListItemViewHolder holder, int position) {
        if (mUsers != null) {
            final HashMap<String, String> currentUser = mUsers.get(position);

            holder.txtViewTemplateName.setText(currentUser.get("email"));
            holder.txtViewCreatedBy.setText("");
        } else {
            holder.txtViewTemplateName.setText("No Users");
        }
    }

    public void setUsers(List<HashMap<String, String>> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        } else {
            return 0;
        }
    }

    class SubscribesToListItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtViewTemplateName;
        private final TextView txtViewCreatedBy;

        private SubscribesToListItemViewHolder(View itemView) {
            super(itemView);
            txtViewTemplateName = itemView.findViewById(R.id.search_list_item_template_name);
            txtViewCreatedBy = itemView.findViewById(R.id.search_list_item_created_by);
        }
    }
}
