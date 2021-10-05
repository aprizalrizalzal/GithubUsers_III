package me.aprizal.githubusers.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.aprizal.githubusers.repository.UsersItemRepository;
import me.aprizal.githubusers.repository.response.UsersResponseItem;

public class FavoriteViewModel extends ViewModel {

    private final UsersItemRepository usersItemRepository;

    public FavoriteViewModel(Application application) {
        usersItemRepository = new UsersItemRepository(application);
    }

    public void delete(UsersResponseItem usersResponseItem) {
        usersItemRepository.delete(usersResponseItem);
    }

    public LiveData<List<UsersResponseItem>> getAllUsersResponseItem() {
        return usersItemRepository.getAllUsersResponseItem();
    }
}
