package com.doctris.care.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Rate;
import com.doctris.care.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class RateViewHolder extends RecyclerView.ViewHolder {
    private CircleImageView imgPatient;
    private TextView tvPatientName;
    private TextView tvDate;
    private TextView tvFeedback;
    private RatingBar ratingBar;
    private final Context context;

    public RateViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingViews(itemView);
    }

    private void bindingViews(View itemView) {
        imgPatient = itemView.findViewById(R.id.patient_image);
        tvPatientName = itemView.findViewById(R.id.patient_name);
        tvDate = itemView.findViewById(R.id.date_feedback);
        tvFeedback = itemView.findViewById(R.id.feedback);
        ratingBar = itemView.findViewById(R.id.ratingBarService);
    }

    public void setRateItem(Rate rateItem) {
        tvPatientName.setText(rateItem.getExpand().getPatient().getName());
        GlideUtil.load(imgPatient, rateItem.getExpand().getPatient().getAvatar());
        tvDate.setText(rateItem.getCreated());
        tvFeedback.setText(rateItem.getDescription());
        ratingBar.setRating(rateItem.getVote());
    }
}
