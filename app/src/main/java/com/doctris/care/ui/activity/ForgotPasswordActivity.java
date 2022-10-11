package com.doctris.care.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.ToastUtil;
import com.doctris.care.utils.ValidateUtil;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etConfirmPassword;
    private String token;
    private Button btnReset;
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
            if (ValidateUtil.isPasswordValid(etPassword) && ValidateUtil.isPassEqual(etPassword, etConfirmPassword)) {
                AlertDialogUtil.loading(this);
                AccountRepository.getInstance().forgot(token, password, confirmPassword).observe(this, status -> {
                    AlertDialogUtil.stop(this);
                    if (status.equals("success")) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialogUtil.error(this, "Yêu cầu thất bại", "Liên kết xác minh đã hết hạn", "OK", KAlertDialog::dismissWithAnimation);
                    }
                });
            }

        } catch (Exception e) {
            ToastUtil.error(this, e.getMessage());
        }
    }

    private String getTokenFromUri(Uri uri) {
        return uri.getQueryParameter("token");
    }
}
