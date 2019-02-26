package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListItemViewHolder> {
    private final LayoutInflater mInflater;
    private List<RemoteTemplate> mTemplates;
    private SearchListAdapter.OnClickRemoteTemplateListListener mListener;

    public SearchListAdapter(Context context, OnClickRemoteTemplateListListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public SearchListItemViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View templateView = mInflater.inflate(R.layout.search_list_item, parent, false);
        return new SearchListAdapter.SearchListItemViewHolder(templateView);
    }

    @Override
    public void onBindViewHolder(SearchListAdapter.SearchListItemViewHolder holder, int position) {
        if (mTemplates != null) {
            final RemoteTemplate currentTemplate = mTemplates.get(position);

            holder.txtViewTemplateName.setText(currentTemplate.getName());
            holder.txtViewCreatedBy.setText(currentTemplate.getCreator());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickRemoteTemplateListItem(currentTemplate);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.txtViewTemplateName.setText("No Template");
        }
    }

    public void setTemplates(List<RemoteTemplate> templates) {
        mTemplates = templates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTemplates != null) {
            return mTemplates.size();
        } else {
            return 0;
        }
    }

    class SearchListItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtViewTemplateName;
        private final TextView txtViewCreatedBy;

        private SearchListItemViewHolder(View itemView) {
            super(itemView);
            txtViewTemplateName = itemView.findViewById(R.id.browse_list_item_template_name);
            txtViewCreatedBy = itemView.findViewById(R.id.browse_list_item_created_by);
        }
    }

    public interface OnClickRemoteTemplateListListener {
        void onClickRemoteTemplateListItem(RemoteTemplate template);
    }
}
