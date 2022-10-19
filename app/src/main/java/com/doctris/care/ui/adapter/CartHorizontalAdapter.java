package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.ui.adapter.holder.CartHorizontalHolder;

import java.util.List;

public class CartHorizontalAdapter<T> extends RecyclerView.Adapter<CartHorizontalHolder<T>> {
    private final List<T> listData;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public CartHorizontalAdapter(List<T> list, Context context) {
        this.listData = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CartHorizontalHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHorizontalHolder<>(layoutInflater.inflate(R.layout.cart_item_horizontal, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHorizontalHolder<T> holder, int position) {
        holder.bindData(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}