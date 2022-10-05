package com.doctris.care.service;

import com.doctris.care.domain.PatientResponse;
import com.doctris.care.entities.Account;
import com.doctris.care.domain.LoginResponse;
import com.doctris.care.entities.Patient;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    // user login api
    @POST("users/auth-via-email")
    Call<LoginResponse> login(@Body RequestBody params);

    // user register api
    @POST("/users")
    Call<Account> register(@Body RequestBody params);


    @GET("collections/patient/records")
    Call<PatientResponse> getPatientInfo(@Header("Authorization") String token);
}
