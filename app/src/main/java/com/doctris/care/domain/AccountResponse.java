package com.doctris.care.domain;

import com.doctris.care.entities.Account;
import com.google.gson.annotations.SerializedName;

public class AccountResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("user")
    private Account account;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
