package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.StudentNotification;
import com.hostelms.utils.DateUtils;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VH> {

    private final List<StudentNotification> list;

    public NotificationAdapter(List<StudentNotification> list) { this.list = list; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        StudentNotification n = list.get(pos);
        h.tvTitle.setText(n.title);
        h.tvBody.setText(n.body);
        h.tvTime.setText(DateUtils.formatDateTime(n.timestamp));

        // Icon + accent color by type
        switch (n.type != null ? n.type : "") {
            case "ROOM_ALLOCATED":
                h.tvIcon.setText("🏠");
                h.tvTitle.setTextColor(Color.parseColor("#10B981"));
                break;
            case "COMPLAINT_UPDATE":
                h.tvIcon.setText("📢");
                h.tvTitle.setTextColor(Color.parseColor("#F59E0B"));
                break;
            default:
                h.tvIcon.setText("📋");
                h.tvTitle.setTextColor(Color.parseColor("#F1F5F9"));
        }

        // Unread indicator
        h.itemView.setBackgroundColor(n.isRead
            ? Color.parseColor("#1A2235")
            : Color.parseColor("#1e3a5f"));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvIcon, tvTitle, tvBody, tvTime;
        VH(View v) {
            super(v);
            tvIcon  = v.findViewById(R.id.tvIcon);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvBody  = v.findViewById(R.id.tvBody);
            tvTime  = v.findViewById(R.id.tvTime);
        }
    }
}
