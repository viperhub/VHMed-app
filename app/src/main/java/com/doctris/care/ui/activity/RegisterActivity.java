package com.doctris.care.ui.activity;

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
        if (ValidateUtil.isEmailValid(etEmail) && ValidateUtil.isPasswordValid(etPassword) && ValidateUtil.isPassEqual(etPassword, etConfirmPassword)) {
            AlertDialogUtil.loading(this);
            AccountRepository.getInstance().register(email, password, confirmPassword).observe(this, status -> {
                AlertDialogUtil.stop(this);
                switch (status) {
                    case "success":
                        AccountRepository.getInstance().requestVerification(email);
                        KAlertDialog.KAlertClickListener listener = sDialog -> {
                            sDialog.dismissWithAnimation();
                            finish();
                        };
                        AlertDialogUtil.success(this, "Đăng ký thành công", "Hãy kiểm tra email và xác minh tài khoản", "OK", listener);
                        break;
                    case "email already exists":
                        AlertDialogUtil.error(this, "Đăng ký thất bại", "Email đã tồn tại", "OK", KAlertDialog::dismissWithAnimation);
                        break;
                    default:
                        AlertDialogUtil.error(this, "Đăng ký thất bại", "Hãy thử lại sau", "OK", KAlertDialog::dismissWithAnimation);
                        break;
                }
            });
        }
    }

    private void login(View view) {
        finish();
    }
    
}
