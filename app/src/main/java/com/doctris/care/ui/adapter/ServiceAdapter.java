package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Service;
import com.doctris.care.ui.adapter.holder.ServiceViewHolder;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> {

    private final List<Service> listService;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public ServiceAdapter(List<Service> listService, Context mContext) {
        this.listService = listService;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceViewHolder(layoutInflater.inflate(R.layout.service_view_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        holder.setServiceItem(listService.get(position));
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }
}
