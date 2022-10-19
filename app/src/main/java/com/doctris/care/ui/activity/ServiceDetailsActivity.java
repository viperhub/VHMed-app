package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctris.care.R;
import com.doctris.care.entities.Service;
import com.doctris.care.repository.ServiceRepository;
import com.doctris.care.utils.GlideUtil;


public class ServiceDetailsActivity extends AppCompatActivity {
    private ImageView imvService;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvCategory;
    private TextView tvDescription;
    private Button btnBack;
    private Button btnBookingService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        bindingView();
        getData();
        bindingAction();
    }


    public void getData(){

        Intent intent = getIntent();
        LiveData<Service> liveData = ServiceRepository.getInstance().getServiceById(intent.getStringExtra("id"));
        liveData.observe(this,service -> {
            if (service != null){
                tvName.setText(service.getName());
                tvPrice.setText(String.valueOf(service.getPrice()));
                tvCategory.setText(service.getName());
                tvDescription.setText(service.getDescription());
                GlideUtil.load(imvService, service.getImage());
            }
        });

    }

    public void bindingView(){
        imvService = findViewById(R.id.iv_image_service);
        tvName = findViewById(R.id.tv_name_service);
        tvPrice = findViewById(R.id.tv_price_service);
        tvCategory = findViewById(R.id.tv_category);
        tvDescription = findViewById(R.id.tv_description);
        btnBack = findViewById(R.id.btn_back);
        btnBookingService = findViewById(R.id.btn_booking_service);

    }

    public void bindingAction(){
        btnBack.setOnClickListener(this::onClickBtnBack);
    }

    private void onClickBtnBack(View view) {
        finish();
    }
}