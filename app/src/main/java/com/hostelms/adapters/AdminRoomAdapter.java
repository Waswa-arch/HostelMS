package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.Room;
import java.util.List;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.VH> {
    private final List<Room> list;
    private final OnItemClick listener;
    public interface OnItemClick { void onClick(Room r); }

    public AdminRoomAdapter(List<Room> list, OnItemClick listener) {
        this.list = list; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Room r = list.get(pos);
        h.tvHostel.setText(r.hostelName);
        h.tvRoom.setText("Room " + r.roomNumber + " — " + r.roomType);
        h.tvOccupied.setText(r.occupied + "/" + r.capacity + " occupied");
        h.tvPrice.setText("KES " + String.format("%.0f", r.pricePerSemester));
        h.tvStatus.setText(r.status);
        int color = "Available".equals(r.status) ? Color.parseColor("#10B981")
                  : "Full".equals(r.status) ? Color.parseColor("#EF4444") : Color.parseColor("#F59E0B");
        h.tvStatus.setTextColor(color);
        h.itemView.setOnClickListener(v -> listener.onClick(r));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvHostel, tvRoom, tvOccupied, tvPrice, tvStatus;
        VH(View v) {
            super(v);
            tvHostel = v.findViewById(R.id.tvHostel); tvRoom = v.findViewById(R.id.tvRoom);
            tvOccupied = v.findViewById(R.id.tvOccupied); tvPrice = v.findViewById(R.id.tvPrice);
            tvStatus = v.findViewById(R.id.tvStatus);
        }
    }
}
