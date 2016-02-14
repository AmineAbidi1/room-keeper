package com.roomkeeper.adapters;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.roomkeeper.R;
import com.roomkeeper.models.Reservation;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.RoomStatus;
import com.roomkeeper.models.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RoomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Room> items = new ArrayList<>();

    private OnItemSelectedListener listener;
    private Map<Long, RoomStatus> statuses = new HashMap<>();


    public RoomsAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Room> items) {
        this.items = items;
    }

    public void setStatuses(Map<Long, RoomStatus> statuses) {
        this.statuses = statuses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new ItemViewHolder(v);
    }

    private Status getStatus(Room room, RoomStatus status) {
        if (status == null || status.getCurrentReservation() == null) {
            return Status.FREE;
        }

        if (status.getCurrentReservation().getNickname().equals("Adam")) {
            return Status.RESERVED_LOCALLY;
        } else {
            if (status.getNoiseLevel() > 50) {
                return Status.RESERVED;
            } else {
                return Status.FREE;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder input, int position) {
        ItemViewHolder holder = (ItemViewHolder) input;

        Room room = items.get(position);
        RoomStatus roomStatus = statuses.get(room.getId());

        // It should be added sensor data!
        Status status = getStatus(room, roomStatus);

        int backGroundColor;
        switch (status) {
            case FREE:
                backGroundColor = Color.GREEN;
                break;
            case RESERVED:
                backGroundColor = Color.RED;
                break;
            case RESERVED_LOCALLY:
                backGroundColor = ContextCompat.getColor(holder.image.getContext(), android.R.color.holo_orange_dark);
                break;
            default:
                backGroundColor = Color.GREEN;
        }

        holder.status.setBackgroundColor(backGroundColor);

        holder.title.setText(room.getTitle());
        holder.time.setText(room.getTime());

        Glide.with(holder.image.getContext())
                .load(room.getImage())
                .placeholder(R.drawable.conference)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface OnItemSelectedListener {
        void onRoomSelectedListener(Room room);

        void onRoomLongClickedListener(Room room);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;

        @Bind(R.id.status_container)
        View status;

        @Bind(R.id.name)
        TextView title;

        @Bind(R.id.time)
        TextView time;

        public ItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRoomSelectedListener(items.get(getAdapterPosition()));
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onRoomLongClickedListener(items.get(getAdapterPosition()));
                    return false;
                }
            });
        }

    }
}
