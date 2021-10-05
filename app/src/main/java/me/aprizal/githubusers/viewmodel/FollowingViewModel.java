package me.aprizal.githubusers.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.aprizal.githubusers.BuildConfig;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.retrofit.ApiConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends ViewModel {

    private final MutableLiveData<List<UsersResponseItem>> _userFollowingItems = new MutableLiveData<>();
    public LiveData<List<UsersResponseItem>> getUsersFollowingItems(){
        return _userFollowingItems;
    }

    private final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    public LiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    private final MutableLiveData<String> showToast = new MutableLiveData<>();
    public LiveData<String> getShowToast(){
        return showToast;
    }

    public void setUser (String login) {
        showLoading.setValue(true);
        Call<List<UsersResponseItem>> listCall = ApiConfig.getApiService().getListFollowingUser(login, BuildConfig.GITHUB_TOKEN);
        listCall.enqueue(new Callback<List<UsersResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<UsersResponseItem>> call, @NonNull Response<List<UsersResponseItem>> response) {
                showLoading.setValue(false);
                if (response.isSuccessful()){
                    if (response.body() !=null){
                        _userFollowingItems.setValue(response.body());
                    }
                }else {
                    if (response.body() !=null){
                        showToast.setValue("Failure Following User " +login );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UsersResponseItem>> call, @NonNull Throwable t) {
                showLoading.setValue(false);
                showToast.setValue("Failure Following User " + t.getMessage() );
            }
        });
    }
}
