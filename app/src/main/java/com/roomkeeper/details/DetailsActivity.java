package com.roomkeeper.details;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.roomkeeper.R;
import com.roomkeeper.details.adapters.ReservationsAdapter;
import com.roomkeeper.models.Reservation;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.RoomStatus;
import com.roomkeeper.models.Status;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements ReservationsAdapter.OnItemSelectedListener {

    public static final String EXTRA_ROOM = "extra_room";
    public static final String EXTRA_STATUS = "extra_status";

    Room room;
    RoomStatus status;

    @Bind(R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.image)
    ImageView image;
    private ReservationsAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        room = (Room) getIntent().getExtras().get(EXTRA_ROOM);
        if (room == null) {
            Toast.makeText(this, "No room selected!", Toast.LENGTH_SHORT).show();
            finish();
        }

        status = (RoomStatus) getIntent().getExtras().get(EXTRA_STATUS);

        initToolbar();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        reservationAdapter = new ReservationsAdapter(this, this);
        reservationAdapter.setRoomStatus(status);
        reservationAdapter.setRoom(room);

        recyclerView.setAdapter(reservationAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle(room.getTitle());

        loadImageForToolbar();
    }

    private void loadImageForToolbar() {
        Glide.with(getApplicationContext())
                .load(room.getImage())
                .placeholder(R.drawable.conference)
                .into(image);
    }

    @Override
    public void onRoomSelectedListener(Reservation room) {

    }
}
