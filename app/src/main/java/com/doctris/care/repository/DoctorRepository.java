package com.doctris.care.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Doctor;
import com.doctris.care.utils.LoggerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepository {
    private static DoctorRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static DoctorRepository getInstance() {
        if (instance == null) {
            instance = new DoctorRepository();
        }
        return instance;
    }

    public LiveData<ListResponse<Doctor>> getDoctors(int page, int perPage, String sort, String filter){
        MutableLiveData<ListResponse<Doctor>> doctors = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<ListResponse<Doctor>> call = RetrofitClient.getInstance().getApi().getDoctorList(page, perPage, sort, filter, "category");
        call.enqueue(new Callback<ListResponse<Doctor>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Doctor>> call, @NonNull Response<ListResponse<Doctor>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Doctor> doctorResponse = response.body();
                    assert doctorResponse != null;
                    doctors.setValue(doctorResponse);
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Doctor>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return doctors;
    }
}
