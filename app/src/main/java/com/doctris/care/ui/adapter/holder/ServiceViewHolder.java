package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Service;
import com.doctris.care.utils.GlideUtil;

public class ServiceViewHolder extends RecyclerView.ViewHolder {

    private TextView tvServiceId;
    private ImageView imgService;
    private TextView tvServiceName;
    private TextView tvCategoryService;
    private TextView tvPriceService;

    private final Context context;

    public ServiceViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingViews(itemView);
        itemView.setOnClickListener(this::onClickServiceItem);
    }

    private void bindingViews(View itemView){
        tvServiceId = itemView.findViewById(R.id.tv_id_service);
        imgService = itemView.findViewById(R.id.img_service);
        tvServiceName = itemView.findViewById(R.id.tv_service);
        tvCategoryService = itemView.findViewById(R.id.tv_category_service);
        tvPriceService = itemView.findViewById(R.id.tv_price_service);
    }

    public void setServiceItem(Service serviceItem){
        tvServiceId.setText(String.valueOf(serviceItem.getId()));
        GlideUtil.load(imgService, serviceItem.getImage());
        tvServiceName.setText(serviceItem.getName());
        tvCategoryService.setText(serviceItem.getExpand().getCategory().getCategoryName());
        tvPriceService.setText(String.valueOf(serviceItem.getPrice()));
    }

    private void onClickServiceItem(View view) {
        // todo
    }
}
