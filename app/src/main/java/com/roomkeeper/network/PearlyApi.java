package com.roomkeeper.network;

import com.roomkeeper.models.Rooms;

import retrofit.Call;
import retrofit.http.GET;

public interface PearlyApi {
    //@GET("/getRooms")
    // Call<Rooms> getRooms();

    //TODO change when pearly api calls are there
    @GET("http://dziubinski.eu/example.json")
    Call<Rooms> getRooms();

    // Call<List<Room>> listRepos(@Path("user") String user);
}