package com.hostelms.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.bumptech.glide.Glide;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Room;
import com.hostelms.models.Hostel;
import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.VH> {

    public interface OnHostelClick { void onClick(Hostel hostel); }

    private final List<Hostel> hostels;
    private final String mode;
    private final OnHostelClick listener;

    private static int getHostelImage(String name) {
        if (name == null) return R.drawable.hostel_baobab;
        String lower = name.toLowerCase();
        if (lower.contains("acacia"))  return R.drawable.hostel_acacia;
        if (lower.contains("savanna")) return R.drawable.hostel_savanna;
        return R.drawable.hostel_baobab;
    }

    private static String getDistance(String name) {
        if (name == null) return "📍 200m from main campus";
        String lower = name.toLowerCase();
        if (lower.contains("acacia"))  return "📍 350m from main campus";
        if (lower.contains("savanna")) return "📍 100m from main campus";
        return "📍 200m from main campus";
    }

    private static String getDescription(String name) {
        if (name == null) return "Quiet study environment.";
        String lower = name.toLowerCase();
        if (lower.contains("acacia"))  return "Modern block completed 2019. All rooms en-suite. Female-only residence.";
        if (lower.contains("savanna")) return "Budget-friendly. Central location. High-energy social atmosphere.";
        return "Classic stone architecture, established 1978. Quiet study environment.";
    }

    public HostelAdapter(List<Hostel> hostels, String mode, OnHostelClick listener) {
        this.hostels = hostels;
        this.mode = mode;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Hostel hostel = hostels.get(pos);
        Context ctx = h.itemView.getContext();

        if (hostel.imageUrl != null && !hostel.imageUrl.isEmpty()) {
            Glide.with(ctx)
                    .load(hostel.imageUrl)
                    .placeholder(R.drawable.hostel_baobab)
                    .into(h.ivImage);
        } else {
            h.ivImage.setImageResource(getHostelImage(hostel.name));
        }

        h.tvName.setText(hostel.name);
        h.tvDistance.setText(getDistance(hostel.name));
        h.tvDescription.setText(hostel.description != null && !hostel.description.isEmpty() 
                ? hostel.description : getDescription(hostel.name));

        AppDatabase db = AppDatabase.getInstance(ctx);
        List<Room> rooms = db.roomDao().getByHostel(hostel.name);
        String gender = "Mixed";
        if (!rooms.isEmpty()) gender = rooms.get(0).gender;
        h.tvGender.setText(gender);

        double minPrice = Double.MAX_VALUE;
        for (Room r : rooms) {
            if (r.pricePerSemester < minPrice) minPrice = r.pricePerSemester;
        }
        h.tvPrice.setText(minPrice < Double.MAX_VALUE
                ? "KES " + String.format("%,.0f", minPrice) + "/sem"
                : "See rooms");

        h.llAmenities.removeAllViews();
        String[] chips;
        String lower = hostel.name != null ? hostel.name.toLowerCase() : "";
        if (lower.contains("acacia")) {
            chips = new String[]{"Wi-Fi", "En-suite", "Gym", "Security", "Lounge"};
        } else if (lower.contains("savanna")) {
            chips = new String[]{"Wi-Fi", "Canteen", "Games Room", "Parking", "Security"};
        } else {
            chips = new String[]{"Wi-Fi", "Laundry", "Common Room", "Canteen", "Security"};
        }
        for (String chip : chips) {
            TextView tv = new TextView(ctx);
            tv.setText(chip);
            tv.setTextSize(11f);
            tv.setTextColor(0xFF94A3B8);
            tv.setBackgroundResource(R.drawable.chip_bg);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMarginEnd(8);
            tv.setLayoutParams(lp);
            tv.setPadding(20, 6, 20, 6);
            h.llAmenities.addView(tv);
        }

        h.btnView.setOnClickListener(v -> listener.onClick(hostel));
    }

    @Override public int getItemCount() { return hostels.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvDistance, tvDescription, tvGender, tvPrice;
        LinearLayout llAmenities;
        Button btnView;

        VH(View v) {
            super(v);
            ivImage       = v.findViewById(R.id.ivHostelImage);
            tvName        = v.findViewById(R.id.tvHostelName);
            tvDistance    = v.findViewById(R.id.tvDistance);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvGender      = v.findViewById(R.id.tvGenderBadge);
            tvPrice       = v.findViewById(R.id.tvPriceFrom);
            llAmenities   = v.findViewById(R.id.llAmenities);
            btnView       = v.findViewById(R.id.btnViewRooms);
        }
    }
}
