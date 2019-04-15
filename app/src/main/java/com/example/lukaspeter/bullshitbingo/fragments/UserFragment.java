package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.RemoteTemplateDetailActivity;
import com.example.lukaspeter.bullshitbingo.adapters.SearchListAdapter;
import com.example.lukaspeter.bullshitbingo.helpers.SimpleAlertDialog;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

public class UserFragment extends Fragment {

    private TextView txtUserName;
    private TextView txtUserMail;
    private FirebaseAuth mAuth;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Firebase
        mAuth = FirebaseAuth.getInstance();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        FirebaseUser user = mAuth.getCurrentUser();

        RecyclerView recyclerView = this.getActivity().findViewById(R.id.recyclerview);

        txtUserName = this.getActivity().findViewById(R.id.textViewName);
        txtUserMail = this.getActivity().findViewById(R.id.textViewMail);

        txtUserName.setText(user.getDisplayName().equals("") ? getString(R.string.example_name) : user.getDisplayName());
        txtUserMail.setText(user.getEmail());
        /*
        final SearchListAdapter adapter = new SearchListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        */
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_user_change_name:
                openChangeUsernameAlert();
                return true;
            case R.id.menu_user_logout:
                logoutUser();
                return true;
            case R.id.menu_user_add_subscriber:
                addSubscriber();
                return true;
        }

        return false;
    }

    private void openChangeUsernameAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.title_change_username));
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                changeUsername(text);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void logoutUser() {

    }

    private void addSubscriber() {

    }

    private void changeUsername(final String name) {
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(),R.string.ok,R.string.success);
                            alert.show();
                            txtUserName.setText(name);
                        } else {
                            SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(),R.string.error,R.string.error);
                            alert.show();
                        }
                    }
                });
    }
}
