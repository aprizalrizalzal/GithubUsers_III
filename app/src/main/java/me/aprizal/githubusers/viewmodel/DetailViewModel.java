package me.aprizal.githubusers.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import me.aprizal.githubusers.BuildConfig;
import me.aprizal.githubusers.repository.UsersItemRepository;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.retrofit.ApiConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private final MutableLiveData<UsersResponseItem> _usersDetailResponseItem = new MutableLiveData<>();
    public LiveData<UsersResponseItem> getDetailUsersResponseItem(){
        return _usersDetailResponseItem;
    }

    private final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    public LiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    private final MutableLiveData<String> showToast = new MutableLiveData<>();
    public LiveData<String> getShowToast(){
        return showToast;
    }

    public void setDetailUser(String login){
        showLoading.setValue(true);
        Call<UsersResponseItem> usersResponseItemCall = ApiConfig.getApiService().getDetailUsers(login, BuildConfig.GITHUB_TOKEN);
        usersResponseItemCall.enqueue(new Callback<UsersResponseItem>() {
            @Override
            public void onResponse(@NonNull Call<UsersResponseItem> call, @NonNull Response<UsersResponseItem> response) {
                showLoading.setValue(false);
                if (response.isSuccessful()){
                    if (response.body() !=null){
                        _usersDetailResponseItem.setValue(response.body());
                    }
                }else {
                    if (response.body() !=null){
                        showToast.setValue("Failure Detail Users " + login);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UsersResponseItem> call, @NonNull Throwable t) {
                showLoading.setValue(false);
                showToast.setValue("Failure Detail Users " +t.getMessage());
            }
        });
    }

    private final UsersItemRepository usersItemRepository;

    public DetailViewModel(Application application){
        usersItemRepository = new UsersItemRepository(application);
    }

    public void insert(UsersResponseItem usersResponseItem) {
        usersItemRepository.insert(usersResponseItem);
    }
}
