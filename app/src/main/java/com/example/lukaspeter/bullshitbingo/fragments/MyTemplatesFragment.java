package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.NewGameActivity;
import com.example.lukaspeter.bullshitbingo.activities.TemplateDetailActivity;
import com.example.lukaspeter.bullshitbingo.adapters.TemplateListAdapter;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.List;

public class MyTemplatesFragment extends Fragment implements TemplateListAdapter.OnClickTemplateListListener {

    private TemplateViewModel mTemplateViewModel;

    public static MyTemplatesFragment newInstance() {
        return new MyTemplatesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_templates, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fragment_my_templates_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent for new Template
                Intent mIntent = new Intent(getActivity(), NewGameActivity.class);
                startActivity(mIntent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = this.getActivity().findViewById(R.id.fragment_my_templates_recyclerview);
        final TemplateListAdapter adapter = new TemplateListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mTemplateViewModel.getAllTemplates().observe(this, new Observer<List<Template>>() {
            @Override
            public void onChanged(@Nullable List<Template> templates) {
                // Update the cached copy of the templates in the adapter.
                adapter.setTemplates(templates);
            }
        });
    }

    @Override
    public void onClickTemplateListItem(final Template template) {

        Intent mIntent = new Intent(getActivity(), TemplateDetailActivity.class);
        mIntent.putExtra("template_id", template.getId());
        startActivity(mIntent);
    }
}
