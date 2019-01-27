package com.example.lukaspeter.bullshitbingo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.fragments.BrowseFragment;
import com.example.lukaspeter.bullshitbingo.fragments.MyGamesFragment;
import com.example.lukaspeter.bullshitbingo.fragments.SearchFragment;

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
                    selectedFragment = MyGamesFragment.newInstance();
                    setTitle(R.string.title_mygames);
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

        // Start Browse Fragment onCreate
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, BrowseFragment.newInstance());
            transaction.commit();
            setTitle(R.string.title_browse);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Create an Instance of AppDatabase
       // AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "local-database").build();

    }
}
