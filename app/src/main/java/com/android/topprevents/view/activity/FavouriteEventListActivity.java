package com.android.topprevents.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.topprevents.R;
import com.android.topprevents.view.fragment.FavouriteListFragment;

public class FavouriteEventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_event_list);
        if(null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.favourite_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, FavouriteListFragment.getInstance(), FavouriteListFragment.TAG).commit();
    }
}
