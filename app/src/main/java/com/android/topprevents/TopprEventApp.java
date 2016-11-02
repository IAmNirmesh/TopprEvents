package com.android.topprevents;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TopprEventApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Create a RealmConfiguration that saves the Realm file in the app's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
