package com.hostelms.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.Attendance;
import com.hostelms.utils.DateUtils;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.VH> {
    private final List<Attendance> list;
    public AttendanceAdapter(List<Attendance> list) { this.list = list; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Attendance a = list.get(pos);
        h.tvStudent.setText(a.studentName + " (ID: " + a.studentId + ")");
        h.tvLocation.setText(a.location);
        h.tvTime.setText(DateUtils.formatDateTime(a.timestamp));
        h.tvType.setText(a.type);
        h.tvType.setTextColor("Entry".equals(a.type) ? Color.parseColor("#10B981") : Color.parseColor("#F59E0B"));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvStudent, tvLocation, tvTime, tvType;
        VH(View v) {
            super(v);
            tvStudent = v.findViewById(R.id.tvStudent); tvLocation = v.findViewById(R.id.tvLocation);
            tvTime = v.findViewById(R.id.tvTime); tvType = v.findViewById(R.id.tvType);
        }
    }
}
