package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.material.card.MaterialCardView;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.Room;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.VH> {
    private final List<Room> list;
    private final OnSelect listener;
    private int selectedPos = -1;
    public interface OnSelect { void onSelect(Room r); }

    public RoomAdapter(List<Room> list, OnSelect listener) {
        this.list = list; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Room r = list.get(pos);
        h.tvHostel.setText(r.hostelName);
        h.tvRoom.setText("Room " + r.roomNumber + " — " + r.roomType + " (" + r.gender + ")");
        h.tvCapacity.setText(r.occupied + "/" + r.capacity + " occupied");
        h.tvPrice.setText("KES " + String.format("%.0f", r.pricePerSemester) + "/sem");
        h.tvAmenities.setText(r.amenities);
        boolean sel = selectedPos == pos;
        h.card.setCardBackgroundColor(sel ? Color.parseColor("#1e3a5f") : Color.parseColor("#1A2235"));
        h.card.setStrokeColor(sel ? Color.parseColor("#F59E0B") : Color.parseColor("#243044"));
        h.itemView.setOnClickListener(v -> {
            selectedPos = pos; notifyDataSetChanged(); listener.onSelect(r);
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView tvHostel, tvRoom, tvCapacity, tvPrice, tvAmenities;
        VH(View v) {
            super(v);
            card = v.findViewById(R.id.card); tvHostel = v.findViewById(R.id.tvHostel);
            tvRoom = v.findViewById(R.id.tvRoom); tvCapacity = v.findViewById(R.id.tvCapacity);
            tvPrice = v.findViewById(R.id.tvPrice); tvAmenities = v.findViewById(R.id.tvAmenities);
        }
    }
}
