package me.aprizal.githubusers.retrofit;

import java.util.List;

import me.aprizal.githubusers.repository.response.UsersResponse;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/users")
    Call<UsersResponse> getListItemsUsers (@Query("q") String items, @Header("Authorization") String auth);

    @GET("users")
    Call<List<UsersResponseItem>> getListUsers (@Header("Authorization") String auth);

    @GET("users/{login}")
    Call<UsersResponseItem> getDetailUsers (@Path("login") String login, @Header("Authorization") String auth);

    @GET("users/{login}/followers")
    Call<List<UsersResponseItem>> getListFollowersUser (@Path("login") String login, @Header("Authorization") String auth);

    @GET("users/{login}/following")
    Call<List<UsersResponseItem>> getListFollowingUser (@Path("login") String login, @Header("Authorization") String auth);

}
