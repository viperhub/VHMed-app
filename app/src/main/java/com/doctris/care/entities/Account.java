package com.doctris.care.entities;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("id")
    private String userId;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("verified")
    private boolean verified;

    public Account() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verified=" + verified +
                '}';
    }

}
