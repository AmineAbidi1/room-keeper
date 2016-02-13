package com.roomkeeper.details;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.roomkeeper.R;
import com.roomkeeper.models.Room;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM = "extra_room";

    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        room = (Room) getIntent().getExtras().get(EXTRA_ROOM);
        if (room == null) {
            Toast.makeText(this, "No room selected!", Toast.LENGTH_SHORT).show();
            finish();
        }

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle(room.getTitle());
//        loadToolbarImage(book.image);
    }
}
