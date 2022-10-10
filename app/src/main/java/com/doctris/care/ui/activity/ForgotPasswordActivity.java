package com.doctris.care.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doctris.care.R;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.utils.ToastUtil;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etConfirmPassword;
    private String token;
    private Button btnReset;
    private TextView tvMessage;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        bindingView();
        Uri uri = getIntent().getData();
        if (uri != null) {
            token = getTokenFromUri(uri);
        }
        bindingAction();
    }

    private void bindingView() {
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirmPassword);
        btnReset = findViewById(R.id.btn_resetPassword);
        tvMessage = findViewById(R.id.message);
        tvBackToLogin = findViewById(R.id.tv_backToLogin);
    }

    private void bindingAction() {
        btnReset.setOnClickListener(this::resetPassword);
        tvBackToLogin.setOnClickListener(this::backToLogin);
    }

    private void backToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void resetPassword(View view) {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        try {
            if (!isCorrectLength(password, confirmPassword)) {
                tvMessage.setText("Password must be at least 8 characters");
            } else if (!isEqual(password, confirmPassword)) {
                tvMessage.setText("Password and confirm password must be the same");
            } else {
                AccountRepository.getInstance().forgot(token, password, confirmPassword).observe(this, status -> {
                    if (status.equals("success")) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        tvMessage.setText("Reset password failed. Link is expired or invalid");
                    }
                });
            }

        } catch (Exception e) {
            ToastUtil.error(this, e.getMessage());
        }
    }

    private boolean isCorrectLength(String password, String confirmPassword) {
        return password.length() >= 8 && confirmPassword.length() >= 8;
    }

    private boolean isEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private String getTokenFromUri(Uri uri) {
        return uri.toString().substring(uri.toString().lastIndexOf("/") + 1);
    }
}
