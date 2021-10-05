package me.aprizal.githubusers.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.aprizal.githubusers.repository.dao.UsersItemDao;
import me.aprizal.githubusers.repository.dao.UsersItemRoomDatabase;
import me.aprizal.githubusers.repository.response.UsersResponseItem;

public class UsersItemRepository {
    private final UsersItemDao _usersItemDao;
    private final ExecutorService executorService;

    public UsersItemRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        UsersItemRoomDatabase db = UsersItemRoomDatabase.getDatabase(application);
        _usersItemDao = db.usersDaoItem();
    }
    public LiveData<List<UsersResponseItem>> getAllUsersResponseItem() {
        return _usersItemDao.getAllUsersResponseItem();
    }

    public void insert(final UsersResponseItem usersResponseItem) {
        executorService.execute(() -> _usersItemDao.insert(usersResponseItem));
    }

    public void delete(final UsersResponseItem usersResponseItem){
        executorService.execute(() -> _usersItemDao.delete(usersResponseItem));
    }
}
