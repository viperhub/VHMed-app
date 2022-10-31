package com.doctris.care.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.doctris.care.R;

public class SuccessActivity extends AppCompatActivity {
    private ImageView ivSuccess;
    private Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        bindingView();
        Glide.with(this)
                .load(R.drawable.success)
                .into(ivSuccess);
        bindingAction();
    }

    private void bindingView() {
        btnView = findViewById(R.id.success_btn);
        ivSuccess = findViewById(R.id.success_frame);
    }

    private void bindingAction() {
        btnView.setOnClickListener(this::onClickView);
    }

    private void onClickView(View view) {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        intent = new Intent(this, BookingHistoryDetail.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }
}
