package com.doctris.care.service;

import com.doctris.care.domain.PatientResponse;
import com.doctris.care.entities.Account;
import com.doctris.care.domain.AccountResponse;
import com.doctris.care.entities.Patient;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    // user login api
    @POST("users/auth-via-email")
    Call<AccountResponse> login(@Body RequestBody params);

    // user register api
    @POST("users")
    Call<AccountResponse> register(@Body RequestBody params);

    // user request verification
    @POST("users/request-verification")
    Call<Void> requestVerification(@Body RequestBody params);

    // user confirm verification
    @POST("users/confirm-verification")
    Call<AccountResponse> verification(@Body RequestBody params);

    // refresh token api
    @POST("users/refresh")
    Call<AccountResponse> refreshToken(@Header("Authorization") String token);

    // user request forgot password api
    @POST("users/request-password-reset")
    Call<Account> requestForgot(@Body RequestBody params);

    // user forgot password api
    @POST("users/confirm-password-reset")
    Call<AccountResponse> forgot(@Body RequestBody params);

    // get patient info
    @GET("collections/patient/records")
    Call<PatientResponse> getPatientInfo(@Header("Authorization") String token);

    // save patient info
    @Multipart
    @POST("collections/patient/records")
    Call<Patient> savePatientInfo(@Header("Authorization") String token,
                                  @Part("name") RequestBody name,
                                  @Part("account") RequestBody account,
                                  @Part("date_of_birth") RequestBody dateOfBirth,
                                  @Part("gender") RequestBody gender,
                                  @Part("phone") RequestBody phone,
                                  @Part("address") RequestBody address,
                                  @Part MultipartBody.Part avatar);
}
