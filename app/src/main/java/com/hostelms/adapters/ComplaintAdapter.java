package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.Complaint;
import com.hostelms.utils.DateUtils;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.VH> {
    public interface OnAdminClick { void onClick(Complaint c); }
    private final List<Complaint> list;
    private final boolean isAdmin;
    private OnAdminClick adminClick;

    public ComplaintAdapter(List<Complaint> list, boolean isAdmin) { this.list = list; this.isAdmin = isAdmin; }
    public ComplaintAdapter(List<Complaint> list, boolean isAdmin, OnAdminClick click) {
        this.list = list; this.isAdmin = isAdmin; this.adminClick = click;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Complaint c = list.get(pos);
        h.tvSubject.setText(c.subject);
        h.tvCategory.setText(c.category + " · " + c.priority);
        h.tvDate.setText(DateUtils.formatDate(c.dateSubmitted));
        h.tvStatus.setText(c.status);
        if (isAdmin) h.tvStudent.setText(c.studentName + " · " + (c.adminResponse != null && !c.adminResponse.isEmpty() ? "Responded" : "No response yet"));
        else h.tvStudent.setVisibility(View.GONE);
        int color = "Resolved".equals(c.status) ? Color.parseColor("#10B981")
                  : "In Progress".equals(c.status) ? Color.parseColor("#F59E0B") : Color.parseColor("#EF4444");
        h.tvStatus.setTextColor(color);
        if (isAdmin && adminClick != null) h.itemView.setOnClickListener(v -> adminClick.onClick(c));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvSubject, tvCategory, tvDate, tvStatus, tvStudent;
        VH(View v) {
            super(v);
            tvSubject = v.findViewById(R.id.tvSubject); tvCategory = v.findViewById(R.id.tvCategory);
            tvDate = v.findViewById(R.id.tvDate); tvStatus = v.findViewById(R.id.tvStatus);
            tvStudent = v.findViewById(R.id.tvStudent);
        }
    }
}
