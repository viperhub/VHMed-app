package com.doctris.care.repository;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.entities.Account;
import com.doctris.care.domain.AccountResponse;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.LoggerUtil;
import com.doctris.care.utils.RequestBodyUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

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
        Call<AccountResponse> call = RetrofitClient.getInstance().getApi().login(RequestBodyUtil.createRequestBody(jsonParams));
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    assert accountResponse != null;
                    if (accountResponse.getAccount().isVerified()) {
                        SharedPrefManager.getInstance().put("token", accountResponse.getToken());
                        SharedPrefManager.getInstance().put("account", accountResponse.getAccount());
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
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }

    public LiveData<String> refreshToken() {
        status = new MutableLiveData<>();
        Call<AccountResponse> call = RetrofitClient.getInstance().getApi().refreshToken("User " + SharedPrefManager.getInstance().get("token", String.class));
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    assert accountResponse != null;
                    SharedPrefManager.getInstance().put("token", accountResponse.getToken());
                    status.setValue("success");
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
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }

    public LiveData<String> requestPasswordReset(String email) {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("email", email);
        Call<Account> call = RetrofitClient.getInstance().getApi().requestForgot(RequestBodyUtil.createRequestBody(jsonParams));
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    status.setValue("success");
                } else {
                    try {
                        status.setValue(response.errorBody().string());
                    } catch (IOException e) {
                        LoggerUtil.e(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }

    public LiveData<String> forgot(String token, String password, String confirmPassword) {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("token", token);
        jsonParams.put("password", password);
        jsonParams.put("passwordConfirm", confirmPassword);
        Call<AccountResponse> call = RetrofitClient.getInstance().getApi().forgot(RequestBodyUtil.createRequestBody(jsonParams));
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    status.setValue("success");
                } else {
                    status.setValue("token expired");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
            }
        });
        return status;
    }

    public LiveData<String> register(String email, String password, String passwordConfirm) {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("email", email);
        jsonParams.put("password", password);
        jsonParams.put("passwordConfirm", passwordConfirm);
        Call<AccountResponse> call = RetrofitClient.getInstance().getApi().register(RequestBodyUtil.createRequestBody(jsonParams));
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    status.setValue("success");
                } else {
                    if (response.code() == 400) {
                        status.setValue("email already exists");
                    } else {
                        status.setValue("error");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }


}
