package me.aprizal.githubusers.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.aprizal.githubusers.repository.response.UsersResponseItem;

@Dao
public interface UsersItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UsersResponseItem usersResponseItem);

    @Delete()
    void delete(UsersResponseItem usersResponseItem);

    @Query("SELECT * from usersresponseitem  ORDER BY id ASC")
    LiveData<List<UsersResponseItem>> getAllUsersResponseItem();
}
