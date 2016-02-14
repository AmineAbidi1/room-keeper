package com.roomkeeper.details;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.roomkeeper.R;
import com.roomkeeper.models.Room;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM = "extra_room";

    Room room;

    @Bind(R.id.image)
    ImageView image;

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

        initToolbar();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}
