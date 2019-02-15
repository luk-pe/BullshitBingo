package com.example.lukaspeter.bullshitbingo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.fragments.BrowseFragment;
import com.example.lukaspeter.bullshitbingo.fragments.MyContentFragment;
import com.example.lukaspeter.bullshitbingo.fragments.SearchFragment;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_browse:
                    selectedFragment = BrowseFragment.newInstance();
                    setTitle(R.string.title_browse);
                    break;
                case R.id.navigation_mygames:
                    selectedFragment = MyContentFragment.newInstance();
                    setTitle(R.string.title_mycontent);
                    break;
                case R.id.navigation_search:
                    selectedFragment = SearchFragment.newInstance();
                    setTitle(R.string.title_search);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init firebase
        FirebaseApp.initializeApp(getApplicationContext());

        // Start Browse Fragment onCreate
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, BrowseFragment.newInstance());
            transaction.commit();
            setTitle(R.string.title_browse);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
}
