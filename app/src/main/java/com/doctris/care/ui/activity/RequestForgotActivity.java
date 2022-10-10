package com.doctris.care.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doctris.care.R;
import com.doctris.care.repository.AccountRepository;

public class RequestForgotActivity extends AppCompatActivity {
    private static final String TAG = "RequestForgotActivity";
    private EditText etEmail;
    private Button btnSend;
    private TextView tvMessage;
    private TextView tvBackToLogin;
    private TextView tvBackToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_forgot);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        etEmail = findViewById(R.id.et_email_confirm);
        btnSend = findViewById(R.id.btn_send);
        tvMessage = findViewById(R.id.message);
        tvBackToLogin = findViewById(R.id.tv_back_to_login);
        tvBackToRegister = findViewById(R.id.tv_back_to_register);
    }

    private void bindingAction() {
        btnSend.setOnClickListener(this::onClickSend);
        tvBackToLogin.setOnClickListener(this::onClickBackToLogin);
        tvBackToRegister.setOnClickListener(this::onClickBackToRegister);
    }

    private void onClickBackToRegister(View view) {
        // todo back to register
    }

    private void onClickBackToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickSend(View view) {
        String email = etEmail.getText().toString();
        AccountRepository.getInstance().requestPasswordReset(email).observe(this, status -> {
            if (status.equals("success")) {
                tvMessage.setText("If your email is registered, you will receive an email with instructions on how to reset your password.");
            } else {
                tvMessage.setText("Cannot send request to reset password");
            }
        });
    }

}
