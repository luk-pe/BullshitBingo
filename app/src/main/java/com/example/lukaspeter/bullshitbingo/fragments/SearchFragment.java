package com.example.lukaspeter.bullshitbingo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.RemoteTemplateDetailActivity;
import com.example.lukaspeter.bullshitbingo.adapters.SearchListAdapter;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;

public class SearchFragment extends Fragment implements SearchListAdapter.OnClickRemoteTemplateListListener {

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = this.getActivity().findViewById(R.id.recyclerview);
        final SearchListAdapter adapter = new SearchListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }
    @Override
    public void onClickRemoteTemplateListItem(RemoteTemplate template) {
        Intent mIntent = new Intent(this.getActivity(), RemoteTemplateDetailActivity.class);
        mIntent.putExtra("template", template);
        this.getActivity().startActivity(mIntent);
    }
}
