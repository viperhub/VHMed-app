package com.doctris.care.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
        verification();
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
                AlertDialogUtil.stop();
                if (status.equals("success")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (status.equals("not verified")) {
                    KAlertDialog.KAlertClickListener listener = (KAlertDialog kAlertDialog) -> {
                        kAlertDialog.dismissWithAnimation();
                        new KAlertDialog(this, KAlertDialog.WARNING_TYPE, 0)
                                .setTitleText("Gửi lại mã xác nhận")
                                .setContentText("Bạn có muốn gửi lại mã xác nhận không?")
                                .setConfirmText("Gửi")
                                .setCancelText("Hủy")
                                .setContentTextSize(15)
                                .setTitleTextSize(20)
                                .setConfirmClickListener(kAlertDialog1 -> {
                                    kAlertDialog1.dismissWithAnimation();
                                    AccountRepository.getInstance().requestVerification(email);
                                    AlertDialogUtil.success(this, "Thành công", "Gửi mã xác minh thành công.", "OK", KAlertDialog::dismissWithAnimation);
                                })
                                .setCancelClickListener(KAlertDialog::dismissWithAnimation)
                                .show();
                    };
                    AlertDialogUtil.warning(this, "Đăng nhập thất bại", "Tài khoản chưa xác minh", "OK", listener);
                } else {
                    AlertDialogUtil.error(this, "Đăng nhập thất bại", "Email hoặc mật khẩu không tồn tại", "OK", KAlertDialog::dismissWithAnimation);
                }
            });
        }
    }

    private void verification() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            String token = uri.getQueryParameter("token");
            AccountRepository.getInstance().confirmVerification(token).observe(this, status -> {
                if (status.equals("success")) {
                    AlertDialogUtil.success(this, "Xác minh thành công", "Tài khoản đã được xác minh", "OK", KAlertDialog::dismissWithAnimation);
                } else {
                    AlertDialogUtil.error(this, "Xác minh thất bại", "Không thể xác minh tài khoản này", "OK", KAlertDialog::dismissWithAnimation);
                }
            });
        }
    }
}