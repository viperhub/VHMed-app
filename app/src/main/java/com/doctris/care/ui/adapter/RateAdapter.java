package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Rate;
import com.doctris.care.ui.adapter.holder.RateViewHolder;

import java.util.List;

public class RateAdapter extends RecyclerView.Adapter<RateViewHolder> {

    private final List<Rate> rateList;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public RateAdapter(List<Rate> rateList, Context mContext) {
        this.rateList = rateList;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RateViewHolder(layoutInflater.inflate(R.layout.feetback_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        holder.setRateItem(rateList.get(position));
    }

    @Override
    public int getItemCount() {
        return rateList.size();
    }
}
