package com.android.topprevents.viewModel;

import android.content.Context;

import com.android.topprevents.api.EventDataModel;
import com.android.topprevents.api.TopprEventRestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListViewModel implements ViewModel {

    private Context mContext;
    private DataListener mDataListener;
    public EventListViewModel(Context context, DataListener listener) {
        mDataListener = listener;
        mContext = context;
    }

    /**
     * Load event list from api
     */
    public void loadEventDataList() {
        TopprEventRestClient.getInstance(mContext).getTopperEventService().getEventList().enqueue(new Callback<EventDataModel>() {
            @Override
            public void onResponse(Call<EventDataModel> call, Response<EventDataModel> response) {
                if(response.isSuccessful()) {
                    if(null != mDataListener)
                        mDataListener.onEventListLoaded(response.body());
                } else {
                    if(null != mDataListener)
                        mDataListener.onEventListFailedToLoad();
                }
            }

            @Override
            public void onFailure(Call<EventDataModel> call, Throwable t) {
                if(null != mDataListener)
                    mDataListener.onEventListFailedToLoad();
            }
        });
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mDataListener = null;
    }

    public interface DataListener {
        void onEventListLoaded(EventDataModel data);
        void onEventListFailedToLoad();
    }
}
