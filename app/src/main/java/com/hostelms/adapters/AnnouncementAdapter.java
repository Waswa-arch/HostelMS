package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.Announcement;
import com.hostelms.utils.DateUtils;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.VH> {
    private final List<Announcement> list;
    public AnnouncementAdapter(List<Announcement> list) { this.list = list; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Announcement a = list.get(pos);
        h.tvTitle.setText((a.isUrgent ? "🔴 URGENT — " : "") + a.title);
        h.tvBody.setText(a.body);
        h.tvAuthor.setText("— " + a.author);
        h.tvDate.setText(DateUtils.formatDate(a.datePosted));
        h.tvTitle.setTextColor(a.isUrgent ? Color.parseColor("#EF4444") : Color.parseColor("#F1F5F9"));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody, tvAuthor, tvDate;
        VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle); tvBody = v.findViewById(R.id.tvBody);
            tvAuthor = v.findViewById(R.id.tvAuthor); tvDate = v.findViewById(R.id.tvDate);
        }
    }
}
