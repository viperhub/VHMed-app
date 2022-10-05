package com.doctris.care.repository;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.entities.Account;
import com.doctris.care.domain.LoginResponse;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.LoggerUtil;
import com.doctris.care.utils.RequestBodyUtil;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {
    private static AccountRepository instance;
    private MutableLiveData<String> status;

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public LiveData<String> login(Account account) throws IOException {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("email", account.getEmail());
        jsonParams.put("password", account.getPassword());
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(RequestBodyUtil.createRequestBody(jsonParams));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    assert loginResponse != null;
                    if(loginResponse.getAccount().isVerified()) {
                        SharedPrefManager.getInstance().put("token", loginResponse.getToken());
                        SharedPrefManager.getInstance().put("account", loginResponse.getAccount());
                        status.setValue("success");
                    } else {
                        status.setValue("not verified");
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        status.setValue(response.errorBody().string());
                    } catch (IOException e) {
                        LoggerUtil.e(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }


}
