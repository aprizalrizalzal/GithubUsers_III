package me.aprizal.githubusers.repository.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.aprizal.githubusers.repository.response.UsersResponseItem;

@Database(entities = {UsersResponseItem.class}, version = 1)
public abstract class UsersItemRoomDatabase extends RoomDatabase {

    public abstract UsersItemDao usersDaoItem();
    private static volatile UsersItemRoomDatabase INSTANCE;

    public static UsersItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsersItemRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UsersItemRoomDatabase.class, "users_database")
                        .build();
            }
        }
        return INSTANCE;
    }

}
