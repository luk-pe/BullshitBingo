package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;
import com.example.lukaspeter.bullshitbingo.models.Template;

import java.util.List;

public class BrowseListAdapter extends RecyclerView.Adapter<BrowseListAdapter.BrowseListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<RemoteTemplate> mTemplates;
    private OnClickRemoteTemplateListListener mListener;

    public BrowseListAdapter(Context context, OnClickRemoteTemplateListListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public BrowseListItemViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View templateView = mInflater.inflate(R.layout.browse_list_item, parent, false);
        return new BrowseListItemViewHolder(templateView);
    }

    @Override
    public void onBindViewHolder(BrowseListItemViewHolder holder, int position) {
        if (mTemplates != null) {
            final RemoteTemplate currentTemplate = mTemplates.get(position);

            holder.txtViewTemplateName.setText(currentTemplate.getName());
            holder.txtViewCreatedBy.setText(currentTemplate.getCreator());
            holder.txtViewDownloadCounter.setText("🔥 " + String.valueOf(currentTemplate.getDownloaded()));
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

    class BrowseListItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtViewTemplateName;
        private final TextView txtViewCreatedBy;
        private final TextView txtViewDownloadCounter;

        private BrowseListItemViewHolder(View itemView) {
            super(itemView);
            txtViewTemplateName = itemView.findViewById(R.id.browse_list_item_template_name);
            txtViewCreatedBy = itemView.findViewById(R.id.browse_list_item_created_by);
            txtViewDownloadCounter = itemView.findViewById(R.id.browse_list_item_download_counter);
        }
    }

    public interface OnClickRemoteTemplateListListener {
        void onClickRemoteTemplateListItem(RemoteTemplate template);
    }
}
