package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.adapters.TemplateListAdapter;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.List;

public class BrowseFragment extends Fragment {
    private TemplateViewModel mTemplateViewModel;
    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ){
        RecyclerView recyclerView = this.getActivity().findViewById(R.id.recyclerview);
        final TemplateListAdapter adapter = new TemplateListAdapter(this.getContext(),null);
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
}
