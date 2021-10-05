package me.aprizal.githubusers.adapter;

import me.aprizal.githubusers.repository.response.UsersResponseItem;

public interface OnItemClickCallback {
    void onItemClicked(UsersResponseItem usersResponseItem);
    void onLinkClicked(UsersResponseItem usersResponseItem);
    void onClearClicked(UsersResponseItem usersResponseItem);
}
