package com.hostelms.adapters;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hostelms.R;
import com.hostelms.models.Hostel;
import java.util.List;

/**
 * NEW adapter for HostelListActivity.
 * Shows hostel image + name always.
 * In "inquiry" mode also shows person-in-charge and contact number.
 */
public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.VH> {
    public interface OnClick { void onClick(Hostel h); }

    private final List<Hostel> list;
    private final String mode;
    private final OnClick listener;

    public HostelAdapter(List<Hostel> list, String mode, OnClick listener) {
        this.list = list; this.mode = mode; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Hostel hostel = list.get(pos);
        h.tvName.setText(hostel.name);

        Glide.with(h.ivImage.getContext())
                .load(hostel.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(h.ivImage);

        if ("inquiry".equals(mode)) {
            h.tvPersonInCharge.setVisibility(View.VISIBLE);
            h.tvContact.setVisibility(View.VISIBLE);
            h.tvPersonInCharge.setText("In charge: " + hostel.personInCharge);
            h.tvContact.setText("Contact: " + hostel.contactNumber);
        } else {
            h.tvPersonInCharge.setVisibility(View.GONE);
            h.tvContact.setVisibility(View.GONE);
        }
        h.itemView.setOnClickListener(v -> listener.onClick(hostel));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvPersonInCharge, tvContact;
        VH(View v) {
            super(v);
            ivImage          = v.findViewById(R.id.ivImage);
            tvName           = v.findViewById(R.id.tvName);
            tvPersonInCharge = v.findViewById(R.id.tvPersonInCharge);
            tvContact        = v.findViewById(R.id.tvContact);
        }
    }
}
