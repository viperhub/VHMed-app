package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCategoryId;
    private TextView tvCategoryName;
    private final Context context;

    public CategoryViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindinfgViews(itemView);
        itemView.setOnClickListener(this::onClickCategoryItem);
    }

    private void bindinfgViews(View itemView){
        tvCategoryId = itemView.findViewById(R.id.tv_id_category);
        tvCategoryName = itemView.findViewById(R.id.tv_name_category);
    }

    public void setCategoryItem(Category category){
        tvCategoryId.setText(category.getId());
        tvCategoryName.setText(category.getCategoryName());
    }

    private void onClickCategoryItem(View view) {
        // todo
    }
}
