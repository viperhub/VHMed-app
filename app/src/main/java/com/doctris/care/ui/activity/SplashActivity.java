package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doctris.care.R;
import com.doctris.care.client.ConnectivityReceiver;
import com.doctris.care.entities.Account;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.repository.PatientRepository;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.ToastUtil;
import com.google.android.material.progressindicator.LinearProgressIndicator;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private Intent intent;
    private Button btnRetry;
    private LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindingView();
        bindingAction();
        onConnection();
    }

    private void bindingView() {
        btnRetry = findViewById(R.id.btnRetry);
        progressIndicator = findViewById(R.id.progressBar);
    }

    private void bindingAction() {
        btnRetry.setOnClickListener(v -> onConnection());
    }

    private void showRetryButton() {
        progressIndicator.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void onConnection() {
        progressIndicator.setVisibility(View.VISIBLE);
        if (ConnectivityReceiver.isConnected()) {
            AccountRepository.getInstance().refreshToken().observe(this, status -> {
                if (status.equals("success")) {
                    Account account = SharedPrefManager.getInstance().get("account", Account.class);
                    assert account != null;

                    PatientRepository.getInstance().getPatientInfo().observe(this, patient -> {
                        if (patient.equals("success")) {
                            intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            finish();
                        } else if (patient.equals("not found")) {
                            intent = new Intent(SplashActivity.this, PatientRegisterActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            finish();
                        } else {
                            ToastUtil.error(this, "Không thể xác minh");
                            showRetryButton();
                        }
                    });
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                }
            });
        } else {
            ToastUtil.error(SplashActivity.this, "Không có kết nối mạng");
            showRetryButton();
        }
    }
}