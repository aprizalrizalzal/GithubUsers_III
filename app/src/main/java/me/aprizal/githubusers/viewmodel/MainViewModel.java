package me.aprizal.githubusers.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.aprizal.githubusers.BuildConfig;
import me.aprizal.githubusers.repository.response.UsersResponse;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.retrofit.ApiConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel{

    private final MutableLiveData<List<UsersResponseItem>> _usersResponseItem = new MutableLiveData<>();

    public LiveData<List<UsersResponseItem>> getUsersResponseItem(){
        return _usersResponseItem;
    }

    private final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    public LiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    private final MutableLiveData<String> showToast = new MutableLiveData<>();
    public LiveData<String> getShowToast(){
        return showToast;
    }

    public void setListSearchUser (String login) {
        showLoading.setValue(true);
        Call<UsersResponse> responseCall = ApiConfig.getApiService().getListItemsUsers(login, BuildConfig.GITHUB_TOKEN);
        responseCall.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<UsersResponse> call, @NonNull Response<UsersResponse> response) {
                showLoading.setValue(false);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        _usersResponseItem.setValue(response.body().getItems());
                    }
                }else {
                    if (response.body() !=null){
                        showToast.setValue("Failure Search User " + login );
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<UsersResponse> call, @NonNull Throwable t) {
                showLoading.setValue(false);
                showToast.setValue("Failure Search Users " + t.getMessage() );
            }
        });
    }

    public void setListUsers () {
        showLoading.setValue(true);
        Call<List<UsersResponseItem>> listCall = ApiConfig.getApiService().getListUsers(BuildConfig.GITHUB_TOKEN);
        listCall.enqueue(new Callback<List<UsersResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<UsersResponseItem>> call, @NonNull Response<List<UsersResponseItem>> response) {
                showLoading.setValue(false);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        _usersResponseItem.setValue(response.body());
                    }
                }else {
                    if (response.body() !=null){
                        showToast.setValue("Failure List Users " );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UsersResponseItem>> call, @NonNull Throwable t) {
                showLoading.setValue(false);
                showToast.setValue("Failure List Users " +t.getMessage());
            }
        });
    }
    private final SettingPreferences preferences;

    public MainViewModel(SettingPreferences preferences) {
        this.preferences = preferences;
    }

    public LiveData<Boolean> getThemeSettings() {
        return LiveDataReactiveStreams.fromPublisher(preferences.getThemeSetting());
    }
}
