package com.doctris.care.ui.activity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.doctris.care.R;
import com.doctris.care.repository.AccountRepository;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_cf_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_back_to_login);
    }

    private void bindingAction() {
        btnRegister.setOnClickListener(this::register);
        tvLogin.setOnClickListener(this::login);
    }

    private void register(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (validateEmail() && validatePassword()) {
            loading();
            AccountRepository.getInstance().register(email, password, confirmPassword).observe(this, status -> {
                if (status.equals("success")) {
                    success();
                } else if (status.equals("email already exists")) {
                    etEmail.setError("Email already exists");
                    stopLoading();
                    etEmail.requestFocus();
                } else {
                    error();
                }
            });
        }
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }
        if (password.length() < 8) {
            etPassword.setError("Password should be at least 6 characters");
            etPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }


    private void login(View view) {
        finish();
    }

    private void loading() {
        new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE, 0)
                .setTitleText("Loading")
                .setTitleTextSize(20)
                .show();
    }

    private void stopLoading() {
        new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE, 0)
                .setTitleText("Loading")
                .setTitleTextSize(20)
                .dismiss();
    }

    private void success() {
        new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE, 0)
                .setTitleText("Register success")
                .setContentText("Please check your email to verify your account")
                .setConfirmText("OK")
                .setConfirmClickListener(kAlertDialog -> {
                    kAlertDialog.dismiss();
                    finish();
                })
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_background, this)
                .show();
    }

    private void error() {
        new KAlertDialog(this, KAlertDialog.ERROR_TYPE, 0)
                .setTitleText("Register failed")
                .setContentText("Please try again")
                .setConfirmText("OK")
                .setConfirmClickListener(KAlertDialog::dismissWithAnimation)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_background, this)
                .show();
    }


}
