package com.doctris.care.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doctris.care.client.RetrofitClient;
import com.doctris.care.domain.ListResponse;
import com.doctris.care.entities.Blog;
import com.doctris.care.storage.SharedPrefManager;
import com.doctris.care.utils.LoggerUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogRepository {
    private static BlogRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static BlogRepository getInstance() {
        if (instance == null) {
            instance = new BlogRepository();
        }
        return instance;
    }

    public LiveData<ListResponse<Blog>> getBlog(int page, int perPage, String sort, String filter){
        MutableLiveData<ListResponse<Blog>> blogs = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<ListResponse<Blog>> call = RetrofitClient.getInstance().getApi().getBlogList(page, perPage, sort, filter, "category");
        call.enqueue((new Callback<ListResponse<Blog>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Blog>> call, @NonNull Response<ListResponse<Blog>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Blog> blogResponse = response.body();
                    assert  blogResponse != null;
                    blogs.setValue(blogResponse);
                    status.setValue(SUCCESS);
                }else{
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Blog>> call, Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        }));
        return blogs;
    }

    public LiveData<Blog> getBlogById(String id) {
        MutableLiveData<Blog> blog = new MutableLiveData<>();
        status = new MutableLiveData<>();
        Call<Blog> call = RetrofitClient.getInstance().getApi().getBlogById(id, "category");
        call.enqueue(new retrofit2.Callback<Blog>() {
            @Override
            public void onResponse(@NonNull Call<Blog> call, @NonNull Response<Blog> response) {
                if (response.isSuccessful()) {
                    Blog blogResponse = response.body();
                    assert  blogResponse != null;
                    blog.setValue(blogResponse);
                    status.setValue(SUCCESS);
                }else{
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(Call<Blog> call, Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return blog;
    }

    public void updateViewerBlog(String id, int viewer){
        MutableLiveData<Blog> blogMutableLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();

        RequestBody viewerRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(viewer));

        Call<Void> call = RetrofitClient.getInstance().getApi().updateViewerBlog("User " + SharedPrefManager.getInstance().get("token", String.class) ,id, viewerRequestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
