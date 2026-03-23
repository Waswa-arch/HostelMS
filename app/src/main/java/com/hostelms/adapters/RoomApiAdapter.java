package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.models.HostelRoom;
import java.util.List;

/**
 * NEW adapter for showing API-sourced rooms in HostelDetailActivity.
 * Handles null listener (inquiry mode – read-only).
 */
public class RoomApiAdapter extends RecyclerView.Adapter<RoomApiAdapter.VH> {
    public interface OnSelect { void onSelect(HostelRoom r); }

    private final List<HostelRoom> list;
    private final OnSelect listener;
    private int selectedPos = -1;

    public RoomApiAdapter(List<HostelRoom> list, OnSelect listener) {
        this.list = list; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_api, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        HostelRoom r = list.get(pos);
        h.tvRoomNumber.setText("Room " + r.number);
        h.tvType.setText(r.type);
        h.tvPrice.setText("RM " + r.price + "/month");
        h.tvAmenities.setText(r.amenities);
        h.tvStatus.setText(r.status.toUpperCase());

        boolean available = "available".equalsIgnoreCase(r.status);
        h.tvStatus.setTextColor(available
                ? Color.parseColor("#10B981") : Color.parseColor("#EF4444"));

        boolean sel = selectedPos == pos;
        h.card.setCardBackgroundColor(sel
                ? Color.parseColor("#1e3a5f") : Color.parseColor("#1A2235"));

        h.itemView.setOnClickListener(v -> {
            if (listener != null) {
                selectedPos = pos;
                notifyDataSetChanged();
                listener.onSelect(r);
            }
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvRoomNumber, tvType, tvPrice, tvAmenities, tvStatus;
        VH(View v) {
            super(v);
            card         = v.findViewById(R.id.card);
            tvRoomNumber = v.findViewById(R.id.tvRoomNumber);
            tvType       = v.findViewById(R.id.tvType);
            tvPrice      = v.findViewById(R.id.tvPrice);
            tvAmenities  = v.findViewById(R.id.tvAmenities);
            tvStatus     = v.findViewById(R.id.tvStatus);
        }
    }
}
