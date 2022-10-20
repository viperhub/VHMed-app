package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Doctor;
import com.doctris.care.ui.adapter.holder.DoctorViewHolder;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorViewHolder> {

    private final List<Doctor> listDoctor;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public DoctorAdapter(List<Doctor> listDoctor, Context mContext) {
        this.listDoctor = listDoctor;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorViewHolder(layoutInflater.inflate(R.layout.doctor_view_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        holder.setDoctorItem(listDoctor.get(position));
    }

    @Override
    public int getItemCount() {
        return listDoctor.size();
    }
}
