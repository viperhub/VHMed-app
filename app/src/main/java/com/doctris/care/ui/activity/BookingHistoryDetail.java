package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;

import com.doctris.care.R;
import com.doctris.care.entities.Booking;
import com.doctris.care.repository.BookingRepository;
import com.doctris.care.utils.GlideUtil;

public class BookingHistoryDetail extends AppCompatActivity {
    private TextView tvBookingId;
    private TextView tvBookingDate;
    private TextView tvBookingTime;
    private TextView tvBookingStatus;
    private TextView tvBookingPaymentStatus;
    private TextView tvBookingService;
    private TextView tvBookingPrice;
    private ImageView ivBookingServiceImage;
    private TextView tvBookingDescription;
    private Button btnBack;
    private CardView serviceCardView;
    private Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_detail);
        bindingView();
        bindingAction();
        getData();
    }

    private void bindingView() {
        tvBookingId = findViewById(R.id.tvBookingId);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvBookingTime = findViewById(R.id.tvBookingTime);
        tvBookingStatus = findViewById(R.id.tvBookingStatus);
        tvBookingPaymentStatus = findViewById(R.id.tvBookingPaymentStatus);
        tvBookingService = findViewById(R.id.tvServiceName);
        tvBookingPrice = findViewById(R.id.tvServicePrice);
        ivBookingServiceImage = findViewById(R.id.imgService);
        tvBookingDescription = findViewById(R.id.tvBookingDescription);
        btnBack = findViewById(R.id.btn_back);
        serviceCardView = findViewById(R.id.service);
    }

    private void bindingAction() {
        btnBack.setOnClickListener(this::onBack);
        serviceCardView.setOnClickListener(this::onService);
    }

    private void onService(View view) {
        Intent intent;
        if (booking.getExpand().getDoctor() != null) {
            intent = new Intent(this, DoctorDetailsActivity.class);
            intent.putExtra("id", booking.getExpand().getDoctor().getId());
        } else {
            intent = new Intent(this, ServiceDetailsActivity.class);
            intent.putExtra("id", booking.getExpand().getService().getId());
        }
        startActivity(intent);
    }

    private void getData() {
        String id = getIntent().getStringExtra("id");
        LiveData<Booking> booking = BookingRepository.getInstance().getBookingById(id);
        booking.observe(this, this::bindingData);
    }

    @SuppressLint("SetTextI18n")
    private void bindingData(Booking booking) {
        this.booking = booking;
        tvBookingId.setText(String.valueOf(booking.getId()));
        String[] dateTime = getDateTime(booking.getDateTime());
        tvBookingDate.setText(dateTime[0]);
        tvBookingTime.setText(dateTime[1].split(":")[0] + ":" + dateTime[1].split(":")[1]);
        switch (booking.getBookingStatus()) {
            case "pending":
                tvBookingStatus.setText("Đang chờ");
                tvBookingStatus.setTextColor(Color.parseColor("#FFA500"));
                break;
            case "cancel":
                tvBookingStatus.setText("Đã hủy");
                tvBookingStatus.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "success":
                tvBookingStatus.setText("Đã hoàn thành");
                tvBookingStatus.setTextColor(Color.parseColor("#008000"));
                break;
        }

        switch (booking.getPaymentStatus()) {
            case "pending":
                tvBookingPaymentStatus.setText("Đang chờ");
                tvBookingPaymentStatus.setTextColor(Color.parseColor("#FFA500"));
                break;
            case "success":
                tvBookingPaymentStatus.setText("Đã thanh toán");
                tvBookingPaymentStatus.setTextColor(Color.parseColor("#008000"));
                break;
            case "cancel":
                tvBookingPaymentStatus.setText("Đã hủy");
                tvBookingPaymentStatus.setTextColor(Color.parseColor("#FF0000"));
                break;
        }

        tvBookingDescription.setText(booking.getDescription());
        if (booking.getExpand().getDoctor() != null) {
            tvBookingService.setText("Bác sĩ : " + booking.getExpand().getDoctor().getName());
            tvBookingPrice.setText("Giá: " + booking.getExpand().getDoctor().getPrice() + " VND");
            GlideUtil.load(ivBookingServiceImage, booking.getExpand().getDoctor().getImage());
        } else {
            tvBookingService.setText("Dịch vụ : " + booking.getExpand().getService().getName());
            tvBookingPrice.setText("Giá: " + booking.getExpand().getService().getPrice() + " VND");
            GlideUtil.load(ivBookingServiceImage, booking.getExpand().getService().getImage());
        }
    }

    private String[] getDateTime(String dateTime) {
        return dateTime.split(" ");
    }

    private void onBack(View view) {
        finish();
    }
}
