package com.hostelms.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import com.hostelms.R;
import com.hostelms.database.entities.Student;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.VH> {
    private final List<Student> list;
    private final OnItemClick listener;
    public interface OnItemClick { void onClick(Student s); }

    public StudentAdapter(List<Student> list, OnItemClick listener) {
        this.list = list; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Student s = list.get(pos);
        h.tvName.setText(s.name);
        h.tvRegNo.setText(s.regNumber);
        h.tvCourse.setText(s.course);
        h.tvRoom.setText(s.roomId > 0 ? "Room assigned" : "Unassigned");
        h.itemView.setOnClickListener(v -> listener.onClick(s));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvRegNo, tvCourse, tvRoom;
        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName); tvRegNo = v.findViewById(R.id.tvRegNo);
            tvCourse = v.findViewById(R.id.tvCourse); tvRoom = v.findViewById(R.id.tvRoom);
        }
    }
}
