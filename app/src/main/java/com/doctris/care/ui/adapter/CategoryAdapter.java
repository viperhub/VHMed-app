package com.doctris.care.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.ui.adapter.holder.CategoryViewHolder;
import com.doctris.care.entities.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    private final List<Category> listCategory;
    private final Context mContext;
    private final LayoutInflater layoutInflater;

    public CategoryAdapter(List<Category> listCategory, Context mContext) {
        this.listCategory = listCategory;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.category_view_item, parent, false);
        return new CategoryViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.setCategoryItem(listCategory.get(position));
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
