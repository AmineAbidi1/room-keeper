package com.roomkeeper.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.roomkeeper.R;
import com.roomkeeper.models.Room;
import com.roomkeeper.models.Status;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RoomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Room> items = new ArrayList<>();
    private OnItemSelectedListener listener;


    public RoomsAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Room> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder input, int position) {
        ItemViewHolder holder = (ItemViewHolder) input;

        Room room = items.get(position);

        int backGroundColor;

        switch (room.getStatus()) {
            case FREE:
                backGroundColor = Color.GREEN;
                break;
            case RESERVED:
                backGroundColor = Color.RED;
                break;
            case RESERVED_LOCALLY:
                backGroundColor = Color.GREEN;
                break;
            default:
                backGroundColor = Color.GREEN;
        }

        holder.status.setBackgroundColor(backGroundColor);

        holder.titile.setText(room.getTitle());
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

    public void setColorForItem(long roomID, Status status) {
        for (Room room : items) {
            if (room.getId() == roomID) {
                room.setStatus(status);
                notifyItemChanged(items.indexOf(room));
            }
        }
    }

    public interface OnItemSelectedListener {
        void onRoomSelectedListener(Room room);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;

        @Bind(R.id.status_container)
        View status;

        @Bind(R.id.name)
        TextView titile;

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
        }


    }
}
