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
import com.doctris.care.entities.Account;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.utils.AlertDialogUtil;
import com.doctris.care.utils.ValidateUtil;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::onClickLogin);
        tvForgotPassword.setOnClickListener(this::onClickForgotPassword);
        tvRegister.setOnClickListener(this::onClickRegister);
    }

    private void onClickRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void onClickForgotPassword(View view) {
        Intent intent = new Intent(this, RequestForgotActivity.class);
        startActivity(intent);
    }

    private void onClickLogin(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        if (ValidateUtil.isEmailValid(etEmail) && ValidateUtil.isPasswordValid(etPassword)) {
            AlertDialogUtil.loading(this);
            AccountRepository.getInstance().login(account).observe(this, status -> {
                AlertDialogUtil.stop(this);
                if (status.equals("success")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (status.equals("not verified")) {
                    AlertDialogUtil.warning(this, "Login failed", "Tài khoản chưa xác minh", "OK", KAlertDialog::dismissWithAnimation);
                } else {
                    AlertDialogUtil.error(this, "Login failed", "Email hoặc mật khẩu không tồn tại", "OK", KAlertDialog::dismissWithAnimation);
                }
            });
        }
    }
}
