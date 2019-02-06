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

    class TemplateViewHolder extends RecyclerView.ViewHolder{
        private final TextView templateItemView;

        private TemplateViewHolder (View itemView){
            super(itemView);
            templateItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Template> mTemplates;

    public TemplateListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TemplateViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(TemplateViewHolder holder, int position){
        if(mTemplates!=null){
            Template current = mTemplates.get(position);
            holder.templateItemView.setText(current.getName());
        } else {
            holder.templateItemView.setText("No Template");
        }
    }

    public void setTemplates(List<Template> templates){
        mTemplates = templates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mTemplates != null){
            return mTemplates.size();
        }else{
            return 0;
        }

    }
}
