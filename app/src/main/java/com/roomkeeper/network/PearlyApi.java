package com.roomkeeper.network;

import com.roomkeeper.models.Reservation;
import com.roomkeeper.models.Reservations;
import com.roomkeeper.models.RoomStatus;
import com.roomkeeper.models.RoomStatuses;
import com.roomkeeper.models.Rooms;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit2.http.Headers;

public interface PearlyApi {

    //TODO change when pearly api calls are there
    @GET("https://pure-dawn-88641.herokuapp.com/api/v1/getRooms.json")
    Call<Rooms> getRooms();

    @GET("https://pure-dawn-88641.herokuapp.com/api/v1/getStatuses.json")
    Call<RoomStatuses> getStatuses();

    @GET("getStatus/{roomID}")
    Call<RoomStatus> getStatusForRoom(@Path("roomID") String roomID);

    @GET("https://pure-dawn-88641.herokuapp.com/api/v1/reservations/{roomID}.json")
    Call<Reservations> getReservations(@Path("roomID") long roomID);


    @Headers("Content-Type: application/json")
    @POST("https://pure-dawn-88641.herokuapp.com/api/v1/bookRoom")
    Call<Reservation> addReservation(@Body Reservation reservation);

}