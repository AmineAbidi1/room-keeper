package com.roomkeeper.adapters;

import android.content.Context;
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
import com.roomkeeper.Tools;
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

    private Context context;

    public RoomsAdapter(Context context, OnItemSelectedListener listener) {
        this.listener = listener;
        this.context = context;
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

        String currentUser = Tools.getCurrentUser(context);

        if (status.getCurrentReservation().getNickname().equals(currentUser)) {
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

        switch (status) {
            case FREE:
                holder.status.setBackgroundColor(Color.GREEN);
                if (roomStatus != null) {
                    if (roomStatus.getOpenTimeLeft() > 0) {
                        String time = Tools.formatTime(roomStatus.getOpenTimeLeft());
                        holder.time.setText(time);
                    } else {
                        holder.time.setText("∞");
                    }

                    holder.description.setText("Room is available for:");
                } else {
                    holder.description.setText("No reservations for today");
                    holder.time.setText("");
                }

                break;
            case RESERVED:
                holder.status.setBackgroundColor(Color.RED);
                if (roomStatus != null) {
                    String time = Tools.formatTime(roomStatus.getReservationTimeLeft());
                    holder.time.setText(time);

                    String description = holder.description.getContext().getString(R.string.will_expire, roomStatus.getCurrentReservation().getNickname());
                    holder.description.setText(description);
                }

                break;
            case RESERVED_LOCALLY:
                holder.status.setBackgroundColor(ContextCompat.getColor(holder.image.getContext(), android.R.color.holo_orange_dark));
                if (roomStatus != null) {
                    String time = Tools.formatTime(roomStatus.getReservationTimeLeft());
                    holder.time.setText(time);
                    holder.description.setText("Your reservation will expire in :");
                }
                break;
            default:
                holder.status.setBackgroundColor(Color.GREEN);
                holder.time.setText("");
                holder.description.setText("No room status");
                break;
        }


        holder.title.setText(room.getTitle());


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

        @Bind(R.id.description)
        TextView description;

        public ItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        listener.onRoomSelectedListener(items.get(getAdapterPosition()));
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        listener.onRoomLongClickedListener(items.get(getAdapterPosition()));
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    return false;
                }
            });
        }

    }
}
