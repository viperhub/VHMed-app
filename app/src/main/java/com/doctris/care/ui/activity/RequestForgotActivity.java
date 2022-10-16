package com.doctris.care.ui.activity;

import android.content.Intent;
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
import com.doctris.care.utils.ValidateUtil;

public class RequestForgotActivity extends AppCompatActivity {
    private EditText etEmail;
    private Button btnSend;
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
        tvBackToLogin = findViewById(R.id.tv_back_to_login);
        tvBackToRegister = findViewById(R.id.tv_back_to_register);
    }

    private void bindingAction() {
        btnSend.setOnClickListener(this::onClickSend);
        tvBackToLogin.setOnClickListener(this::onClickBackToLogin);
        tvBackToRegister.setOnClickListener(this::onClickBackToRegister);
    }

    private void onClickBackToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickBackToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickSend(View view) {
        String email = etEmail.getText().toString();
        if (ValidateUtil.isEmailValid(etEmail)) {
            AlertDialogUtil.loading(this);
            AccountRepository.getInstance().requestPasswordReset(email).observe(this, status -> {
                AlertDialogUtil.stop();
                if (status.equals("success")) {
                    AlertDialogUtil.success(this, "Thành công", "Nếu email đã đăng ký. Chúng tôi sẽ gửi email xác minh", "OK", KAlertDialog::dismissWithAnimation);
                } else {
                    AlertDialogUtil.error(this, "Lỗi", "Không thể xử lý", "OK", KAlertDialog::dismissWithAnimation);
                }
            });
        }

    }

}
