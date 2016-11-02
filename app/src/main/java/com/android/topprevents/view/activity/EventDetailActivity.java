package com.android.topprevents.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.topprevents.R;
import com.android.topprevents.api.Website;
import com.android.topprevents.databinding.ActivityEventDetailBinding;
import com.squareup.picasso.Picasso;

public class EventDetailActivity extends AppCompatActivity {

    public static String KEY_EVENT_DATA = "KEY_EVENT_DATA";
    private Website mWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEventDetailBinding mActivityEventDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail);
        mWebsite = getIntent().getParcelableExtra(KEY_EVENT_DATA);
        mActivityEventDetailBinding.setData(mWebsite);
        setSupportActionBar(mActivityEventDetailBinding.toolbar);
        if(null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Picasso.with(this).load(mWebsite.getImage()).into(mActivityEventDetailBinding.image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share) {
            shareText();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,getSharingText());
        startActivity(intent);
    }

    private String getSharingText() {
        String text = "Hey Checkout this challenge from HackerEarth: " + mWebsite.getName() + "\n"
                + "Experience: " + mWebsite.getExperience() + "\n"
                + "CTC: 12L - 24L per annum" + "\n"
                + "Description: " + mWebsite.getDescription() + "\n"
                + "Link: www.hackerearth.com" + "\n"
                + "Category: " + mWebsite.getCategory();

        return text;
    }
}
