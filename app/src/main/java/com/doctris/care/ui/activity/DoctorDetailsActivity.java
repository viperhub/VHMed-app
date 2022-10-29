package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.doctris.care.R;
import com.doctris.care.entities.Doctor;
import com.doctris.care.repository.DoctorRepository;
import com.doctris.care.utils.GlideUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        bindingView();
        getData();
        bindingAction();
    }

    private void bindingView(){
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
    }

    @SuppressLint("SetTextI18n")
    public void getData(){

        Intent intent = getIntent();
        LiveData<Doctor> liveData = DoctorRepository.getInstance().getDoctorById(intent.getStringExtra("id"));
        liveData.observe(this,doctor -> {
            if (doctor != null){
                tvCategory.setText(doctor.getExpand().getCategory().getCategoryName());
                tvName.setText(doctor.getName());
                tvGender.setText(doctor.isGender() ? "Nam" : "Nữ");
                tvBirthday.setText(convertDate(doctor.getDateOfBirth()));
                tvPhone.setText("0" + doctor.getPhone());
                tvAddress.setText(doctor.getAddress());
                tvDescription.setText(doctor.getDescription());
                tvPrice.setText(String.valueOf(doctor.getPrice()));
                GlideUtil.load(doctorImage, doctor.getImage());
            }
        });

    }

    private String convertDate(String date) {
        String[] dates = date.split(" ");
        String[] day = dates[0].split("-");
        return day[2] + "/" + day[1] + "/" + day[0];
    }

    public void bindingAction(){
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
