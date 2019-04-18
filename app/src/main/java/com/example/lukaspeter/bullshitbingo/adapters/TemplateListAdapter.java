package com.example.lukaspeter.bullshitbingo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.Template;

import java.util.List;

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.TemplateViewHolder> {

    private final LayoutInflater mInflater;
    private List<Template> mTemplates;
    private OnClickTemplateListListener mListener;

    public TemplateListAdapter(Context context, OnClickTemplateListListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View templateView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TemplateViewHolder(templateView);
    }

    @Override
    public void onBindViewHolder(TemplateViewHolder holder, int position) {
        if (mTemplates != null) {
            final Template currentTemplate = mTemplates.get(position);

            holder.templateItemView.setText(currentTemplate.getName());
            holder.templateCreatedItemView.setText(currentTemplate.getCreator());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickTemplateListItem(currentTemplate);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.templateItemView.setText("No Template");
        }
    }

    public void setTemplates(List<Template> templates) {
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

    class TemplateViewHolder extends RecyclerView.ViewHolder {
        private final TextView templateItemView;
        private final TextView templateCreatedItemView;

        private TemplateViewHolder(View itemView) {
            super(itemView);
            templateItemView = itemView.findViewById(R.id.textView);
            templateCreatedItemView = itemView.findViewById(R.id.textView2);
        }
    }

    public interface OnClickTemplateListListener {
        void onClickTemplateListItem(Template template);
    }
}
