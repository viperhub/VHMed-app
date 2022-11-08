package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.domain.ExpandResponse;
import com.doctris.care.entities.Booking;
import com.doctris.care.entities.Rate;
import com.doctris.care.repository.BookingRepository;
import com.doctris.care.repository.RateRepository;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.GlideUtil;
import com.doctris.care.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackActivity extends AppCompatActivity {
    private CircleImageView ivService;
    private TextView tvServiceName;
    private TextView tvServiceDate;

    private RatingBar rbService;
    private TextView edtComment;
    private Button btnSubmit;

    private Booking booking;
    private int rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_rate);
        bindingView();
        getData();
        bindingAction();
    }

    private void getData() {
        String id = getIntent().getStringExtra("id");
        LiveData<Booking> booking = BookingRepository.getInstance().getBookingById(id);
        booking.observe(this, this::bindingData);
    }

    @SuppressLint("SetTextI18n")
    private void bindingData(Booking booking) {
        this.booking = booking;
        tvServiceDate.setText("Thời gian đặt lịch: " + booking.getDateTime().split(" ")[0]);
        if (booking.getExpand().getDoctor() != null) {
            tvServiceName.setText("Bác sĩ: " + booking.getExpand().getDoctor().getName());
            GlideUtil.load(ivService, booking.getExpand().getDoctor().getImage());
        } else {
            tvServiceName.setText("Dịch vụ" + booking.getExpand().getService().getName());
            GlideUtil.load(ivService, booking.getExpand().getService().getImage());
        }
    }

    private void bindingAction() {
        btnSubmit.setOnClickListener(this::onSubmit);
        rbService.setOnRatingBarChangeListener((ratingBar, v, b) -> rating = (int) v);

    }

    private void onSubmit(View view) {
        if (onValidate()) {
            AlertDialogUtil.loading(this);
            Rate rate = new Rate();
            rate.setVote(rating);
            rate.setDescription(edtComment.getText().toString());
            ExpandResponse expand = new ExpandResponse();
            if (booking.getExpand().getDoctor() != null) {
                expand.setDoctor(booking.getExpand().getDoctor());
            } else {
                expand.setService(booking.getExpand().getService());
            }
            rate.setExpand(expand);
            BookingRepository.getInstance().feedbackBooking(booking.getId());
            RateRepository.getInstance().saveRate(rate);
            KAlertDialog.KAlertClickListener listener = sDialog -> {
                sDialog.dismissWithAnimation();
                Intent intent = new Intent(this, BookingHistoryDetail.class);
                intent.putExtra("id", booking.getId());
                startActivity(intent);
                finish();
            };
            AlertDialogUtil.success(this, "Thông báo", "Cảm ơn bạn đã đánh giá dịch vụ của chúng tôi", "Đóng", listener);
        }
    }

    private boolean onValidate() {
        if (rating == 0) {
            ToastUtil.warning(this, "Bạn chưa đánh giá");
            return false;
        } else if (edtComment.getText().toString().isEmpty()) {
            ToastUtil.warning(this, "Bạn chưa nhập nhận xét");
            return false;
        }
        return true;
    }

    private void bindingView() {
        ivService = findViewById(R.id.imgService);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvServiceDate = findViewById(R.id.tvServiceDate);
        rbService = findViewById(R.id.ratingBarService);
        edtComment = findViewById(R.id.edtComment);
        btnSubmit = findViewById(R.id.btnSend);
    }


}
