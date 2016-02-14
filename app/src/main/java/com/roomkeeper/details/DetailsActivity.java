package com.roomkeeper.details;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.roomkeeper.R;
import com.roomkeeper.details.adapters.ReservationsAdapter;
import com.roomkeeper.models.Reservation;
import com.roomkeeper.models.Reservations;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.RoomStatus;
import com.roomkeeper.network.PearlyApi;
import com.roomkeeper.settings.SettingsFragment;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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
    private PearlyApi pearlyApi;

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

        Retrofit retrofit = new Retrofit.Builder()
                //TODO change whenever pearly api is ready
                .baseUrl("http://dziubinski.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        pearlyApi = retrofit.create(PearlyApi.class);

        status = (RoomStatus) getIntent().getExtras().get(EXTRA_STATUS);

        initToolbar();

        setUpAddingReservationDialogs();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        reservationAdapter = new ReservationsAdapter(this, this);
        reservationAdapter.setRoomStatus(status);
        reservationAdapter.setRoom(room);

        recyclerView.setAdapter(reservationAdapter);

        downloadReservations();
    }

    public void downloadReservations() {
        Call<Reservations> roomsCall = pearlyApi.getReservations(room.getId());

        //asynchronous call
        roomsCall.enqueue(new Callback<Reservations>() {
            @Override
            public void onResponse(Response<Reservations> response, Retrofit retrofit) {
                if (response == null) {
                    return;
                }
                List<Reservation> rooms = response.body().getReservations();
                reservationAdapter.setItems(rooms);
                reservationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Error", "Failed to download Statuse, response: " + t.getMessage());
            }
        });

    }

    private void setUpAddingReservationDialogs() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("ROOM RESERVATION");

                final Button chooseDateButton = new Button(DetailsActivity.this);
                chooseDateButton.setText("Set Date");

                final Calendar calendar = Calendar.getInstance();
                final int[] yearRead = {calendar.get(Calendar.YEAR)};
                final int[] monthRead = {calendar.get(Calendar.MONTH)};
                final int[] dayRead = {calendar.get(Calendar.DAY_OF_MONTH)};
                final int[] hourRead = {calendar.get(Calendar.HOUR_OF_DAY)};
                final int[] minuteRead = {calendar.get(Calendar.MINUTE)};

                chooseDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(DetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                chooseDateButton.setText("Date set to:    " + year + " " + monthOfYear + " " + dayOfMonth);

                                yearRead[0] = year;
                                monthRead[0] = monthOfYear;
                                dayRead[0] = dayOfMonth;
                            }
                        }, yearRead[0], monthRead[0], dayRead[0]).show();
                    }
                });


                final Button chooseTimeButton = new Button(DetailsActivity.this);
                chooseTimeButton.setText("Set Time");
                chooseTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Create a new instance of TimePickerDialog and return it
                        new TimePickerDialog(DetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                chooseTimeButton.setText("Time set to:    " + hourOfDay + " : " + minute);
                                hourRead[0] = hourOfDay;
                                minuteRead[0] = minute;
                            }
                        }, hourRead[0], minuteRead[0], DateFormat.is24HourFormat(DetailsActivity.this)).show();
                    }
                });

                TextView textView = new TextView(DetailsActivity.this);
                textView.setText("Enter estimated meeting time in minutes");
                int tvPadding = getResources().getDimensionPixelSize(R.dimen.dialog_message_padding_top);
                textView.setPadding(tvPadding, tvPadding, tvPadding, tvPadding);
                textView.setTextColor(Color.BLACK);

                final EditText editText = new EditText(DetailsActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                LinearLayout linearLayout = new LinearLayout(DetailsActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(tvPadding, 0, tvPadding, 0);

                linearLayout.addView(chooseDateButton);
                linearLayout.addView(chooseTimeButton);
                linearLayout.addView(textView);
                linearLayout.addView(editText);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DetailsActivity.this);

                        calendar.set(yearRead[0],
                                monthRead[0],
                                dayRead[0],
                                hourRead[0],
                                minuteRead[0]);

                        String nickname = prefs.getString(SettingsFragment.NICKNAME, "");

                        if (nickname.equals("")) {
                            Toast.makeText(getApplicationContext(), "You have to set Nickname in settings to proceed with reservations", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            return;
                        }

                        if (!editText.getText().toString().equals("")) {
                            Integer durationInMinutes = Integer.valueOf(editText.getText().toString());
                            Reservation reservation = new Reservation(room.getId(),
                                    prefs.getString(SettingsFragment.NICKNAME, ""),
                                    prefs.getString(SettingsFragment.PHONE_NO, ""),
                                    prefs.getString(SettingsFragment.SPARK_ID, ""),
                                    calendar.getTimeInMillis(),
                                    calendar.getTimeInMillis() + durationInMinutes * 1000 * 60);


                            pearlyApi.addReservation(
                                    reservation)
                                    .enqueue(new Callback<Reservation>() {
                                        @Override
                                        public void onResponse(Response<Reservation> response, Retrofit retrofit) {

                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Log.d("", "");

                                        }
                                    });

                            Toast.makeText(getApplicationContext(), "Reserved", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Duration not set, try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(linearLayout);

                builder.create().show();
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

    @Override
    public void onRoomSelectedListener(Reservation room) {

    }
}
