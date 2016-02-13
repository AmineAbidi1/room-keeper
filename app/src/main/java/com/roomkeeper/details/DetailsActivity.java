package com.roomkeeper.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roomkeeper.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM = "extra_room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
