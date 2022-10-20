package com.doctris.care.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Service;
import com.doctris.care.utils.LoggerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepository {
    private static ServiceRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static ServiceRepository getInstance() {
        if (instance == null) {
            instance = new ServiceRepository();
        }
        return instance;
    }

    public LiveData<ListResponse<Service>> getServices(int page, int perPage, String sort, String filter) {
        MutableLiveData<ListResponse<Service>> services = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<ListResponse<Service>> call = RetrofitClient.getInstance().getApi().getServiceList(page, perPage, sort, filter, "category");
        call.enqueue(new Callback<ListResponse<Service>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Service>> call, @NonNull Response<ListResponse<Service>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Service> serviceResponse = response.body();
                    assert serviceResponse != null;
                    services.setValue(serviceResponse);
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Service>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return services;
    }
    public LiveData<Service> getServiceById(String id) {
        MutableLiveData<Service> service = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<Service> call = RetrofitClient.getInstance().getApi().getServiceById(id, "category");
        call.enqueue(new retrofit2.Callback<Service>() {
            @Override
            public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                if (response.isSuccessful()) {
                    Service serviceResponse = response.body();
                    assert serviceResponse != null;
                    service.setValue(serviceResponse);
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Service> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return service;
    }


}
