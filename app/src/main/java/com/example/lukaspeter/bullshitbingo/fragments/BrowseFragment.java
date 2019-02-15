package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.RemoteTemplateDetailActivity;
import com.example.lukaspeter.bullshitbingo.adapters.BrowseListAdapter;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.List;

public class BrowseFragment extends Fragment implements BrowseListAdapter.OnClickRemoteTemplateListListener {
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = this.getActivity().findViewById(R.id.recyclerview);
        final BrowseListAdapter adapter = new BrowseListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mTemplateViewModel.getAllRemoteTemplates().observe(this, new Observer<List<RemoteTemplate>>() {
            @Override
            public void onChanged(@Nullable List<RemoteTemplate> templates) {
                // Update the cached copy of the templates in the adapter.
                adapter.setTemplates(templates);
            }
        });
    }

    @Override
    public void onClickRemoteTemplateListItem(RemoteTemplate template) {
        Intent mIntent = new Intent(this.getActivity(), RemoteTemplateDetailActivity.class);
        mIntent.putExtra("template", template);
        this.getActivity().startActivity(mIntent);
    }
}
