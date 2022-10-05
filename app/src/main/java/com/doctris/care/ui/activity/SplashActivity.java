package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doctris.care.R;
import com.doctris.care.client.ConnectivityReceiver;
import com.doctris.care.entities.Account;
import com.doctris.care.repository.PatientRepository;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.ToastUtil;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        onCheckConnection();
        bindingView();
    }

    private void bindingView() {
        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(v -> onCheckConnection());
    }

    private void showRetryButton() {
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void onCheckConnection() {
        if (ConnectivityReceiver.isConnected()) {
            if (ConnectivityReceiver.isConnected()) {
                PatientRepository.getInstance().getPatientInfo().observe(this, status -> {
                    if (status.equals("success")) {
                        intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (status.equals("not found")) {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.error(this, status);
                        showRetryButton();
                    }
                });
            } else {
                ToastUtil.error(SplashActivity.this, "No internet connection");
                showRetryButton();
            }
        } else {
            ToastUtil.error(SplashActivity.this, "No internet connection");
            showRetryButton();
        }
    }
}