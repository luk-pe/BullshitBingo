package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.GameActivity;
import com.example.lukaspeter.bullshitbingo.activities.TemplateDetailActivity;
import com.example.lukaspeter.bullshitbingo.adapters.TemplateListAdapter;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.List;

public class MyTemplatesFragment extends Fragment implements TemplateListAdapter.OnClickTemplateListListener {

    private TemplateViewModel mTemplateViewModel;

    public static MyTemplatesFragment newInstance() {
        MyTemplatesFragment fragment = new MyTemplatesFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_templates, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fragment_my_templates_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO LUKAS Intent for new Template
                Snackbar.make(view, "Start Intent for new Template", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
