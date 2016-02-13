package com.roomkeeper.network;

import com.roomkeeper.models.RoomStatuses;
import com.roomkeeper.models.Rooms;

import retrofit.Call;
import retrofit.http.GET;

public interface PearlyApi {
    //@GET("/getRooms")
    // Call<Rooms> getRooms();

    //TODO change when pearly api calls are there
    @GET("http://public.dziubinski.eu/pearly/getRooms.json")
    Call<Rooms> getRooms();

    //TODO change when pearly api calls are there
    @GET("http://public.dziubinski.eu/pearly/getStatuses.json")
    Call<RoomStatuses> getStatuses();

    // Call<List<Room>> listRepos(@Path("user") String user);
}