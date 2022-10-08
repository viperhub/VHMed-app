package com.doctris.care.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doctris.care.R;
import com.doctris.care.entities.Account;
import com.doctris.care.repository.AccountRepository;
import com.doctris.care.utils.ToastUtil;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvForgotPassword;
    private TextView tvMessage;

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
        tvMessage = findViewById(R.id.message);
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::onClickLogin);
    }

    @SuppressLint("SetTextI18n")
    private void onClickLogin(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        try {
            AccountRepository.getInstance().login(account).observe(this, status -> {
                if (status.equals("success")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (status.equals("not verified")) {
                    tvMessage.setText("Please verify your email");
                } else {
                    tvMessage.setText("Login failed");
                }
            });
        } catch (IOException e) {
            ToastUtil.error(this, "Can't connect to server");
        }
    }
}
