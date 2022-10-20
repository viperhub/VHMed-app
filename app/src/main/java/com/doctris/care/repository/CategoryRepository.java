package com.doctris.care.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Category;
import com.doctris.care.utils.LoggerUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static CategoryRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    public LiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> categories = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<ListResponse<Category>> call = RetrofitClient.getInstance().getApi().getCategoryList();
        call.enqueue(new Callback<ListResponse<Category>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Category>> call, @NonNull Response<ListResponse<Category>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Category> categoryResponse = response.body();
                    assert categoryResponse != null;
                    categories.setValue(categoryResponse.getItems());
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Category>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return categories;
    }

}
