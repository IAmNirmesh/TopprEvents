package com.android.topprevents.viewModel;

import android.content.Context;

import com.android.topprevents.api.Website;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavouriteListViewModel implements ViewModel {
    private Context mContext;
    private DataListener mDataListener;

    public FavouriteListViewModel(Context context, DataListener listener) {
        mDataListener = listener;
        mContext = context;
    }

    /**
     * Load event list from api
     */
    public void loadFavEventDataList() {
        Realm realmDb = Realm.getDefaultInstance();
        RealmResults<Website> resultDbs = realmDb.where(Website.class).findAll();
        if (null != mDataListener) {
            mDataListener.onFavEventListLoaded(resultDbs);
        }
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mDataListener = null;
    }

    public interface DataListener {
        void onFavEventListLoaded(RealmResults<Website> resultDbs);
    }
}
