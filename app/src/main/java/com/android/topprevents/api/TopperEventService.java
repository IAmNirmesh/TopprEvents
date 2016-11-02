package com.android.topprevents.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopperEventService {
    @GET("api/toppr_events?type=json&query=list_events")
    Call<EventDataModel> getEventList();
}
