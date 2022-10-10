package com.doctris.care.service;

import com.doctris.care.domain.PatientResponse;
import com.doctris.care.entities.Account;
import com.doctris.care.domain.AccountResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    // user login api
    @POST("users/auth-via-email")
    Call<AccountResponse> login(@Body RequestBody params);

    // user register api
    @POST("/users")
    Call<Account> register(@Body RequestBody params);

    // refresh token api
    @POST("users/refresh")
    Call<AccountResponse> refreshToken(@Header("Authorization") String token);

    // user request forgot password api
    @POST("users/request-password-reset")
    Call<Account> requestForgot(@Body RequestBody params);

    // user forgot password api
    @POST("users/confirm-password-reset")
    Call<AccountResponse> forgot(@Body RequestBody params);

    @GET("collections/patient/records")
    Call<PatientResponse> getPatientInfo(@Header("Authorization") String token);
}
