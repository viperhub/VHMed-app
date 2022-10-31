package com.doctris.care.service;

import com.doctris.care.domain.AccountResponse;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Account;
import com.doctris.care.entities.Blog;
import com.doctris.care.entities.Booking;
import com.doctris.care.entities.Category;
import com.doctris.care.entities.Doctor;
import com.doctris.care.entities.Patient;
import com.doctris.care.entities.Service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<ListResponse<Patient>> getPatientInfo(@Header("Authorization") String token);

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

    // update patient info
    @Multipart
    @PATCH("collections/patient/records/{id}")
    Call<Patient> updatePatientInfo(@Header("Authorization") String token,
                                    @Path("id") String id,
                                    @Part("name") RequestBody name,
                                    @Part("account") RequestBody account,
                                    @Part("date_of_birth") RequestBody dateOfBirth,
                                    @Part("gender") RequestBody gender,
                                    @Part("phone") RequestBody phone,
                                    @Part("address") RequestBody address,
                                    @Part MultipartBody.Part avatar);

    // service list
    @GET("collections/service/records")
    Call<ListResponse<Service>> getServiceList(@Query("page") int page,
                                               @Query("perPage") int perPage,
                                               @Query("sort") String sort,
                                               @Query("filter") String filter,
                                               @Query("expand") String expand);

    // blog list
    @GET("collections/blog/records")
    Call<ListResponse<Blog>> getBlogList(@Query("page") int page,
                                         @Query("perPage") int perPage,
                                         @Query("sort") String sort,
                                         @Query("filter") String filter,
                                         @Query("expand") String expand);

    // category list
    @GET("collections/category/records")
    Call<ListResponse<Category>> getCategoryList();

    // service by id
    @GET("collections/service/records/{id}")
    Call<Service> getServiceById(@Path("id") String id,
                                 @Query("expand") String expand);

    // blog by id
    @GET("collections/blog/records/{id}")
    Call<Blog> getBlogById(@Path("id") String id,
                           @Query("expand") String expand);

    // doctor list
    @GET("collections/doctor/records")
    Call<ListResponse<Doctor>> getDoctorList(@Query("page") int page,
                                             @Query("perPage") int perPage,
                                             @Query("sort") String sort,
                                             @Query("filter") String filter,
                                             @Query("expand") String expand);

    // doctor by id
    @GET("collections/doctor/records/{id}")
    Call<Doctor> getDoctorById(@Path("id") String id,
                               @Query("expand") String expand);

    // booking history
    @GET("collections/booking/records")
    Call<ListResponse<Booking>> getBookingHistory(@Header("Authorization") String token,
                                                  @Query("page") int page,
                                                  @Query("perPage") int perPage,
                                                  @Query("sort") String sort,
                                                  @Query("filter") String filter,
                                                  @Query("expand") String expand);

    // booking history detail
    @GET("collections/booking/records/{id}")
    Call<Booking> getBookingHistoryDetail(@Header("Authorization") String token,
                                          @Path("id") String id,
                                          @Query("expand") String expand);

    // increase viewer blog
    @Multipart
    @PATCH("collections/blog/records/{id}")
    Call<Void> updateViewerBlog (@Header("Authorization") String token,
                                 @Path("id") String id,
                                 @Part("viewer") RequestBody viewer);

    // save booking infomation
    @POST("collections/booking/records")
    Call<Booking> saveBooking(@Header("Authorization") String token,
                              @Body RequestBody params);
}
