package com.roomkeeper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomkeeper.R;
import com.roomkeeper.models.Room;

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

        holder.titile.setText(room.getTitle());
        holder.time.setText(room.getTime());

        //holder.description.setText(room.getDescription());
        //holder.time.setText(room.getStatus());
        //ImageLoader.getInstance().displayImage(room.getImage(), holder.image);
    }
/*
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        ItemViewHolder ourHolder = (ItemViewHolder) holder;
        Room room = items.get(position);

        ourHolder.time.setText(room.getTime());

    }*/

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView titile;

        @Bind(R.id.description)
        TextView description;

        @Bind(R.id.time)
        TextView time;

        @Bind(R.id.image)
        public ImageView image;

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

    public interface OnItemSelectedListener {
        void onRoomSelectedListener(Room room);
    }
}
