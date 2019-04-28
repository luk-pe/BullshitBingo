package com.example.lukaspeter.bullshitbingo.fragments;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.activities.LoginActivity;
import com.example.lukaspeter.bullshitbingo.adapters.SubscribesToAdapter;
import com.example.lukaspeter.bullshitbingo.helpers.SimpleAlertDialog;
import com.example.lukaspeter.bullshitbingo.models.DataRepository;
import com.example.lukaspeter.bullshitbingo.models.FirebaseDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;
import java.util.List;

public class UserFragment extends Fragment {
    private TextView txtUserName;
    private TextView txtUserMail;
    private FirebaseAuth mAuth;
    private SubscribesToAdapter adapter;

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

        final RecyclerView recyclerView = this.getActivity().findViewById(R.id.recyclerview);
        adapter = new SubscribesToAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtUserName = this.getActivity().findViewById(R.id.textViewName);
        txtUserMail = this.getActivity().findViewById(R.id.textViewMail);

        if (user.getDisplayName() != null) {
            txtUserName.setText(user.getDisplayName().isEmpty() ? getString(R.string.example_name) : user.getDisplayName());
        } else {
            txtUserName.setText(getString(R.string.example_name));
        }
        txtUserMail.setText(user.getEmail());

        // Load subscribers
        FirebaseDB.getInstance().getSubscribesTo().observe(getActivity(), new Observer<List<HashMap<String, String>>>() {
            @Override
            public void onChanged(@Nullable List<HashMap<String, String>> subscribes_to) {
                adapter.setUsers(subscribes_to);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_user_change_name:
                openChangeUsernameAlert();
                return true;
            case R.id.menu_user_logout:
                openLogoutAlert();
                return true;
            case R.id.menu_user_add_subscriber:
                openAddSubscriberAlert();
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

    private void openLogoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.menu_user_logout));
        builder.setMessage(getString(R.string.ask_logout));

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutUser();
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
        mAuth.signOut();
        DataRepository mDataRepository = new DataRepository(this.getActivity().getApplication());
        mDataRepository.clearLokalDB();
        Intent login = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(login);
    }

    private void openAddSubscriberAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.title_change_username));
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(getContext(), "Please insert email-address!", Toast.LENGTH_SHORT).show();
                } else {
                    addSubscriber(text);
                }
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

    private void addSubscriber(String mail) {
        FirebaseDB.getInstance().findUserByMail(mail).observe(getActivity(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, String> user) {
                if (user == null) {
                    SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(), R.string.error, R.string.user_not_found);
                    alert.show();
                } else {
                    FirebaseDB.getInstance().addSubscriber(user).observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean success) {
                            if (success) {
                                SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(), R.string.success, R.string.success);
                                alert.show();
                                // Load subscribers
                                FirebaseDB.getInstance().getSubscribesTo().observe(getActivity(), new Observer<List<HashMap<String, String>>>() {
                                    @Override
                                    public void onChanged(@Nullable List<HashMap<String, String>> subscribes_to) {
                                        adapter.setUsers(subscribes_to);
                                    }
                                });
                            } else {
                                SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(), R.string.error, R.string.error);
                                alert.show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void changeUsername(final String name) {
        final FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseDB.getInstance().updateUser(user.getUid(), name).observe(getActivity(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(@Nullable Boolean success) {
                                    if (success) {
                                        SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(), R.string.ok, R.string.success);
                                        alert.show();
                                        txtUserName.setText(name);
                                    } else {
                                        SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(), R.string.error, R.string.error);
                                        alert.show();
                                    }
                                }
                            });

                        } else {
                            SimpleAlertDialog alert = new SimpleAlertDialog(getActivity(), R.string.error, R.string.error);
                            alert.show();
                        }
                    }
                });
    }
}
