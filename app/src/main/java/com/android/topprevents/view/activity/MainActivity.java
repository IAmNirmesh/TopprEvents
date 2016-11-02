package com.android.topprevents.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.topprevents.view.fragment.EventListFragment;
import com.android.topprevents.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(null != getSupportActionBar())
            getSupportActionBar().setTitle(R.string.home_title);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, EventListFragment.getInstance(), EventListFragment.TAG).commit();

    }
}
