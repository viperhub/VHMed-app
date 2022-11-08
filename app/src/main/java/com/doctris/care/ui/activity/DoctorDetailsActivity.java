package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctris.care.R;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Doctor;
import com.doctris.care.entities.Rate;
import com.doctris.care.repository.DoctorRepository;
import com.doctris.care.repository.RateRepository;
import com.doctris.care.ui.adapter.RateAdapter;
import com.doctris.care.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDetailsActivity extends AppCompatActivity {
    private CircleImageView doctorImage;
    private TextView tvCategory;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvBirthday;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvDescription;
    private TextView tvPrice;
    private Button btnBook;
    private Button btnBack;
    private RecyclerView rvComment;
    private String doctorId;
    private int page = 1;
    private List<Rate> listRate;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        listRate = new ArrayList<>();
        bindingView();
        getData();
        bindingAction();
    }

    private void bindingView() {
        doctorImage = findViewById(R.id.iv_image_doctor);
        tvCategory = findViewById(R.id.tv_category);
        tvName = findViewById(R.id.tv_name_doctor);
        tvGender = findViewById(R.id.tv_gender);
        tvBirthday = findViewById(R.id.tv_birthday);
        tvPhone = findViewById(R.id.tv_phone);
        tvAddress = findViewById(R.id.tv_address);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_price_doctor);
        btnBook = findViewById(R.id.btn_booking_doctor);
        btnBack = findViewById(R.id.btn_back);
        rvComment = findViewById(R.id.rv_comment);
        ratingBar = findViewById(R.id.ratingBar);
    }

    @SuppressLint("SetTextI18n")
    public void getData() {
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("id");
        LiveData<Doctor> liveData = DoctorRepository.getInstance().getDoctorById(doctorId);
        liveData.observe(this, doctor -> {
            if (doctor != null) {
                tvCategory.setText(doctor.getExpand().getCategory().getCategoryName());
                tvName.setText(doctor.getName());
                tvGender.setText(doctor.isGender() ? "Nam" : "Nữ");
                tvBirthday.setText(convertDate(doctor.getDateOfBirth()));
                tvPhone.setText("0" + doctor.getPhone());
                tvAddress.setText(doctor.getAddress());
                tvDescription.setText(doctor.getDescription());
                tvPrice.setText(String.valueOf(doctor.getPrice()));
                GlideUtil.load(doctorImage, doctor.getImage());
                initComment();
            }
        });
    }

    private void initComment() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setHasFixedSize(true);
        getRateData(listRate);
    }


    private void getRateData(List<Rate> listRate) {
        LiveData<ListResponse<Rate>> liveData = RateRepository.getInstance().getRates(page, 50, "-created", "(doctor='" + doctorId + "')");
        liveData.observe(this, listResponse -> {
            if (listResponse != null) {
                listRate.addAll(listResponse.getItems());
                ratingBar.setRating(getRating(listRate));
                RateAdapter rateAdapter = new RateAdapter(listRate, this);
                rvComment.setAdapter(rateAdapter);
                rvComment.setNestedScrollingEnabled(false);
                rvComment.setFocusable(false);
            }
        });
    }

    private float getRating(List<Rate> listRate) {
        AtomicInteger sum = new AtomicInteger();
        listRate.forEach(rate -> sum.addAndGet(rate.getVote()));
        return (float) sum.get() / listRate.size();
    }

    private String convertDate(String date) {
        String[] dates = date.split(" ");
        String[] day = dates[0].split("-");
        return day[2] + "/" + day[1] + "/" + day[0];
    }

    public void bindingAction() {
        btnBack.setOnClickListener(this::onClickBtnBack);
        btnBook.setOnClickListener(this::onClickBtnBook);
    }

    private void onClickBtnBook(View view) {
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("type", "doctor");
        startActivity(intent);
    }

    private void onClickBtnBack(View view) {
        finish();
    }
}
