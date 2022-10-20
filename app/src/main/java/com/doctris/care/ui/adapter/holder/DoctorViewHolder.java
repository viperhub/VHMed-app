package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Doctor;

import com.doctris.care.ui.activity.DoctorDetailsActivity;
import com.doctris.care.utils.GlideUtil;

public class DoctorViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDoctorId;
    private ImageView imgDoctor;
    private TextView tvDoctorName;
    private TextView tvCategoryDoctor;
    private TextView tvPriceDoctor;

    private final Context context;

    public DoctorViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingViews(itemView);
        itemView.setOnClickListener(this::onClickDoctorItem);
    }

    private void bindingViews(View itemView) {
        tvDoctorId = itemView.findViewById(R.id.tv_id_doctor);
        imgDoctor = itemView.findViewById(R.id.img_doctor);
        tvDoctorName = itemView.findViewById(R.id.tv_doctor);
        tvCategoryDoctor = itemView.findViewById(R.id.tv_category_doctor);
        tvPriceDoctor = itemView.findViewById(R.id.tv_price_doctor);
    }

    public void setDoctorItem(Doctor doctorItem) {
        tvDoctorId.setText(String.valueOf(doctorItem.getId()));
        GlideUtil.load(imgDoctor, doctorItem.getImage());
        tvDoctorName.setText(doctorItem.getName());
        tvCategoryDoctor.setText(doctorItem.getExpand().getCategory().getCategoryName());
        tvPriceDoctor.setText(String.valueOf(doctorItem.getPrice()));
    }

    private void onClickDoctorItem(View view) {
        Intent intent = new Intent(context, DoctorDetailsActivity.class);
        intent.putExtra("id", tvDoctorId.getText().toString());
        context.startActivity(intent);
    }
}
