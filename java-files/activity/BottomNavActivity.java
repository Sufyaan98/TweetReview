package com.example.tweetreview_v2.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.bottom_fragments.ResultFragment;
import com.example.tweetreview_v2.bottom_fragments.SearchFragment;
import com.example.tweetreview_v2.bottom_fragments.TweetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Author: Sufyaan Siddique
 *
 * This class is responsible for holding all Bottom Navigation fragments
 * and switches between them saving their states.
 */

public class BottomNavActivity extends AppCompatActivity {

    Context context = this;
    final Fragment fragment1 = new SearchFragment(context);
    final Fragment fragment2 = new ResultFragment();
    final Fragment fragment3 = new TweetFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();

        final Intent i = getIntent();
        String data = i.getStringExtra("FromWatson");

        if (data !=null && data.contentEquals("1")) {
            Log.d("test", "Arrived at BottomNavActivity. Switching to ResultsFragment");
            bottomNavigationView.setSelectedItemId(R.id.nav_results);
            fm.beginTransaction().hide(active).show(fragment2).commit();
            active = fragment2;
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_search:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.nav_results:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.nav_tweets:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                }
                return false;
            }
        });
    }
}