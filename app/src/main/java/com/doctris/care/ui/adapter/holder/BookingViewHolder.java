package com.doctris.care.ui.adapter.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.entities.Booking;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvStatus;
    private TextView tvId;
    private Context context;

    public BookingViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingView(itemView);
    }

    private void bindingView(View itemView) {
        tvName = itemView.findViewById(R.id.tv_booking_name);
        tvDate = itemView.findViewById(R.id.tv_booking_date);
        tvTime = itemView.findViewById(R.id.tv_booking_time);
        tvStatus = itemView.findViewById(R.id.tv_booking_status);
        tvId = itemView.findViewById(R.id.tv_booking_id);
    }

    @SuppressLint("SetTextI18n")
    public void bindData(Booking booking) {
        tvId.setText(String.valueOf(booking.getId()));
        if (booking.getExpand().getDoctor() != null) {
            tvName.setText("Bác sĩ : " + booking.getExpand().getDoctor().getName());
        } else {
            tvName.setText("Dịch vụ : " + booking.getExpand().getService().getName());
        }
        String[] dateTime = getDateTime(booking.getDateTime());
        tvDate.setText(dateTime[0]);
        tvTime.setText(dateTime[1].split(":")[0] + ":" + dateTime[1].split(":")[1]);
        if (booking.getBookingStatus().equals("pending")) {
            tvStatus.setTextColor(Color.parseColor("#fcba03"));
            tvStatus.setText("Chưa xác nhận");
        } else if (booking.getBookingStatus().equals("cancel")) {
            tvStatus.setTextColor(Color.parseColor("#fc0303"));
            tvStatus.setText("Đã hủy");
        } else {
            tvStatus.setTextColor(Color.parseColor("#030ffc"));
            tvStatus.setText("Đã xác nhận");
        }
    }

    private String[] getDateTime(String dateTime) {
        return dateTime.split(" ");
    }
}
