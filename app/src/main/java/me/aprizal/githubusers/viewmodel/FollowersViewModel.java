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

public class FollowersViewModel extends ViewModel {

    private final MutableLiveData<List<UsersResponseItem>> _userFollowersItems = new MutableLiveData<>();
    public LiveData<List<UsersResponseItem>> getUsersFollowersItems(){
        return _userFollowersItems;
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
        Call<List<UsersResponseItem>>listCall = ApiConfig.getApiService().getListFollowersUser(login, BuildConfig.GITHUB_TOKEN);
        listCall.enqueue(new Callback<List<UsersResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<UsersResponseItem>> call, @NonNull Response<List<UsersResponseItem>> response) {
                showLoading.setValue(false);
                if (response.isSuccessful()){
                    if (response.body() !=null){
                        _userFollowersItems.setValue(response.body());
                    }
                }else {
                    if (response.body() !=null){
                        showToast.setValue("Failure Followers User " +login );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UsersResponseItem>> call, @NonNull Throwable t) {
                showLoading.setValue(false);
                showToast.setValue("Failure Followers User " + t.getMessage() );
            }
        });
    }
}
