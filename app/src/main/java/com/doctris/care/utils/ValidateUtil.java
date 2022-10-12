package com.doctris.care.utils;

import android.widget.EditText;

public class ValidateUtil {
    private ValidateUtil() {
    }

    public static boolean isEmailValid(EditText etEmail) {
        String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            etEmail.requestFocus();
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    public static boolean isPasswordValid(EditText etPassword) {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        } else if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            etPassword.requestFocus();
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    public static boolean isPassEqual(EditText etPassword, EditText etConfirmPassword) {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password doesn't match");
            etConfirmPassword.requestFocus();
            return false;
        } else {
            etConfirmPassword.setError(null);
            return true;
        }
    }
}
