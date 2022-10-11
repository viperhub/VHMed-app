package com.doctris.care.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

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
        onCheckConnection();
    }

    private void bindingView() {
        btnRetry = findViewById(R.id.btnRetry);
        progressIndicator = findViewById(R.id.progressBar);
    }

    private void bindingAction() {
        btnRetry.setOnClickListener(v -> onCheckConnection());
    }

    private void showRetryButton() {
        progressIndicator.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void onCheckConnection() {
        progressIndicator.setVisibility(View.VISIBLE);
        if (ConnectivityReceiver.isConnected()) {
            if (ConnectivityReceiver.isConnected()) {
                AccountRepository.getInstance().refreshToken().observe(this, status -> {
                    if (status.equals("success")) {
                        Account account = SharedPrefManager.getInstance().get("account", Account.class);
                        assert account != null;

                        PatientRepository.getInstance().getPatientInfo().observe(this, patient -> {
                            if (patient.equals("success")) {
                                intent = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (patient.equals("not found")) {
                                // only for test purpose only. Will be removed in the future when have screen for patient registration
                                intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtil.error(this, "Something went wrong");
                                showRetryButton();
                            }
                        });
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
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