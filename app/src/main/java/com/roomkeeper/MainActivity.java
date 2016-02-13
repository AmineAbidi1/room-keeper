package com.roomkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roomkeeper.adapters.RoomsAdapter;
import com.roomkeeper.details.DetailsActivity;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.Rooms;
import com.roomkeeper.network.PearlyApi;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<Rooms>, RoomsAdapter.OnItemSelectedListener {

    private static final String LOG_TAG = "MainActivityTag";

    @Bind(R.id.list)
    RecyclerView recyclerView;
    private RoomsAdapter roomsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        roomsAdapter = new RoomsAdapter(this);
        recyclerView.setAdapter(roomsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        downloadRooms();

    }

    private void downloadRooms() {
        Retrofit retrofit = new Retrofit.Builder()
                //TODO change whenever pearly api is ready
                .baseUrl("http://dziubinski.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PearlyApi pearlyApi = retrofit.create(PearlyApi.class);

        //TODO change call whenever pearly api is ready
        Call<Rooms> call = pearlyApi.getRooms();

        //asynchronous call
        call.enqueue(this);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response<Rooms> response, Retrofit retrofit) {
        List<Room> rooms = response.body().getRooms();
        for (Room room : rooms) {
            Log.d(LOG_TAG, room.getTitle());
        }

        roomsAdapter.setItems(rooms);
        roomsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(LOG_TAG, "Failed to download, response: " + t.getMessage());
    }

    @Override
    public void onRoomSelectedListener(Room room) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ROOM, room);
        startActivity(intent);
    }
}
