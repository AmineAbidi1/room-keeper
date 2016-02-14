package com.roomkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.roomkeeper.adapters.RoomsAdapter;
import com.roomkeeper.details.DetailsActivity;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.RoomStatus;
import com.roomkeeper.models.RoomStatuses;
import com.roomkeeper.models.Rooms;
import com.roomkeeper.models.Status;
import com.roomkeeper.network.PearlyApi;
import com.roomkeeper.settings.SettingsActivity;
import com.roomkeeper.splashscreen.SplashScreen;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements RoomsAdapter.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "MainActivityTag";

    @Bind(R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.splash)
    SplashScreen splash;

    @Bind(R.id.refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private RoomsAdapter roomsAdapter;
    private PearlyApi pearlyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                //TODO change whenever pearly api is ready
                .baseUrl("http://dziubinski.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pearlyApi = retrofit.create(PearlyApi.class);

        int columnsNumber;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnsNumber = 3;
        } else {
            columnsNumber = 2;
        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, columnsNumber);
        recyclerView.setLayoutManager(mLayoutManager);

        roomsAdapter = new RoomsAdapter(this);
        recyclerView.setAdapter(roomsAdapter);

        setupSwipeRefresh(swipeRefreshLayout);


        if (savedInstanceState == null) {
            splash.startAnimation();
        } else {
            splash.setVisibility(View.GONE);
        }

        downloadData();

    }

    public void setupSwipeRefresh(SwipeRefreshLayout view) {
        view.setOnRefreshListener(this);
        view.setEnabled(true);
        view.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putBoolean("oldInstance", true);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void downloadData() {


        //TODO change call whenever pearly api is ready
        Call<Rooms> roomsCall = pearlyApi.getRooms();

        //asynchronous call
        roomsCall.enqueue(new Callback<Rooms>() {
            @Override
            public void onResponse(Response<Rooms> response, Retrofit retrofit) {
                List<Room> rooms = response.body().getRooms();
                for (Room room : rooms) {
                    Log.d(LOG_TAG, room.getTitle());
                }

                roomsAdapter.setItems(rooms);
                roomsAdapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Failed to download Statuse, response: " + t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        Call<RoomStatuses> statusesCall = pearlyApi.getStatuses();

        statusesCall.enqueue(new Callback<RoomStatuses>() {
            @Override
            public void onResponse(Response<RoomStatuses> response, Retrofit retrofit) {
                List<RoomStatus> statuses = response.body().getStatuses();
                for (RoomStatus roomStatus : statuses) {
                    long roomID = roomStatus.getRoomID();
                    Log.d(LOG_TAG, "roomID:" + roomID);

                    if (roomStatus.getNoiseLevel() > 10000) {
                        roomsAdapter.setColorForItem(roomID, Status.FREE);
                    } else {
                        roomsAdapter.setColorForItem(roomID, Status.RESERVED);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Failed to download Statuses, response: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRoomSelectedListener(Room room) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ROOM, room);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        downloadData();
    }

    @Override
    public void onRoomLongClickedListener(Room room) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("QUICK RESERVATION");

        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO create reservation object
//                pearlyApi.addReservation(new Reservation());
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setView(editText);

        builder.setMessage("Enter estimated meeting time in minutes");
        builder.create().show();
    }
}
