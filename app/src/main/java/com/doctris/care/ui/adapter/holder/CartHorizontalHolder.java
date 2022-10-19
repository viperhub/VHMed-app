package com.doctris.care.ui.adapter.holder;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Doctor;
import com.doctris.care.entities.Service;
import com.doctris.care.utils.GlideUtil;

public class CartHorizontalHolder<T> extends RecyclerView.ViewHolder {
    private ImageView ivImage;
    private TextView tvName;
    private TextView tvCategory;
    private final Context context;

    public CartHorizontalHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingView(itemView);
    }

    private void bindingView(View itemView) {
        ivImage = itemView.findViewById(R.id.iv_image);
        tvName = itemView.findViewById(R.id.tv_name);
        tvCategory = itemView.findViewById(R.id.tv_category);
    }

    public void bindData(T data) {
        if (data instanceof Doctor) {
            bindDataDoctor((Doctor) data);
        } else if (data instanceof Service) {
            bindDataService((Service) data);
        }
    }

    private void bindDataService(Service data) {
        GlideUtil.load(ivImage, data.getImage());
        tvName.setText(data.getName());
        tvCategory.setText(data.getExpand().getCategory().getCategoryName());
    }

    private void bindDataDoctor(Doctor data) {
        GlideUtil.load(ivImage, data.getImage());
        tvName.setText(data.getName());
        tvCategory.setText(data.getCategory().getCategoryName());
    }
}