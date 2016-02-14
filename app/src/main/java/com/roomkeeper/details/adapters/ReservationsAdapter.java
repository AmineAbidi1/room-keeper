package com.roomkeeper.details.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomkeeper.R;
import com.roomkeeper.models.Reservation;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.RoomStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReservationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d MMMM yyyy, cccc");
    final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("K:m a");
    private final int VIEW_TYPE_SENSOR_DATA = 1;
    private final int VIEW_TYPE_RESERVATION = 2;
    private final int VIEW_TYPE_ROOM_DATA = 3;
    private Context context;
    private List<Reservation> items = new ArrayList<>();
    private OnItemSelectedListener listener;
    private RoomStatus status;
    private Room room;

    public ReservationsAdapter(Context context, OnItemSelectedListener listener) {
        this.context = context;
        this.listener = listener;

        items.add(new Reservation(1, 1, "Mark", System.currentTimeMillis() + 10000, System.currentTimeMillis() + 20000));
        items.add(new Reservation(2, 1, "John", System.currentTimeMillis() + 20000, System.currentTimeMillis() + 30000));
        items.add(new Reservation(3, 1, "Greg", System.currentTimeMillis() + 30000, System.currentTimeMillis() + 40000));
        items.add(new Reservation(4, 1, "Johanson", System.currentTimeMillis() + 40000, System.currentTimeMillis() + 50000));
        items.add(new Reservation(5, 1, "Andrew", System.currentTimeMillis() + 50000, System.currentTimeMillis() + 60000));
        items.add(new Reservation(6, 1, "Rosa", System.currentTimeMillis() + 60000, System.currentTimeMillis() + 70000));

    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setRoomStatus(RoomStatus status) {
        this.status = status;
    }

    public void setItems(List<Reservation> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_SENSOR_DATA) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor_data, parent, false);
            return new SensorDataViewHolder(v);

        } else if (viewType == VIEW_TYPE_ROOM_DATA) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_data, parent, false);
            return new RoomDataViewHolder(v);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
            return new ReservationViewHolder(v);
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return VIEW_TYPE_SENSOR_DATA;
        }

        if (position == 1) {
            return VIEW_TYPE_ROOM_DATA;
        }

        return VIEW_TYPE_RESERVATION;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder input, int position) {

        int type = getItemViewType(position);
        if (type == VIEW_TYPE_RESERVATION) {

            ReservationViewHolder holder = (ReservationViewHolder) input;

            Reservation reservation = items.get(position);

            String description = holder.description.getContext().getString(R.string.reservation_pattern, reservation.getNickname());
            holder.description.setText(description);

            Date date = new Date(reservation.getStartTime());
            holder.date.setText(DATE_FORMAT.format(date));
            holder.start.setText(TIME_FORMAT.format(date));

            Date dateEnd = new Date(reservation.getStartTime());
            holder.start.setText(TIME_FORMAT.format(dateEnd));
        } else if (type == VIEW_TYPE_SENSOR_DATA) {

            SensorDataViewHolder holder = (SensorDataViewHolder) input;

            if (status != null) {

                holder.temerature.setText(context.getString(R.string.temperature, status.getTemperature()));
                holder.humidity.setText(context.getString(R.string.humidity, status.getHumidity()));
                holder.bright.setText(context.getString(R.string.brightness, status.getLuminocity()));
                holder.noise.setText(context.getString(R.string.noise, status.getNoiseLevel()));
            } else {
                holder.temerature.setText("?");
                holder.humidity.setText("?");
                holder.bright.setText("?");
                holder.noise.setText("?");
            }
        } else if (type == VIEW_TYPE_ROOM_DATA) {
            RoomDataViewHolder holder = (RoomDataViewHolder) input;
            holder.seats.setText(String.valueOf(room.getCapacity()));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemSelectedListener {
        void onRoomSelectedListener(Reservation room);
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.time_start)
        TextView start;
        @Bind(R.id.time_end)
        TextView end;
        @Bind(R.id.title)
        TextView description;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRoomSelectedListener(items.get(getAdapterPosition()));
                }
            });

        }
    }


    class RoomDataViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.peopleCount)
        TextView seats;

        public RoomDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SensorDataViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.brightLevel)
        TextView bright;

        @Bind(R.id.humidityLevel)
        TextView humidity;

        @Bind(R.id.tempLevel)
        TextView temerature;

        @Bind(R.id.noiseLevel)
        TextView noise;

        public SensorDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
