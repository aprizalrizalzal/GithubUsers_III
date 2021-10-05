package me.aprizal.githubusers.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.databinding.ListRowUsersBinding;
import me.aprizal.githubusers.repository.response.UsersResponseItem;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private final List<UsersResponseItem> usersResponseItems = new ArrayList<>();
    private final OnItemClickCallback onItemClickCallback;
    private boolean activate = true;

    @SuppressLint("NotifyDataSetChanged")
    public void activateButtons(boolean activate) {
        this.activate = activate;
        notifyDataSetChanged();
    }

    public UsersAdapter(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<UsersResponseItem> items) {
        usersResponseItems.clear();
        usersResponseItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_users, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        holder.bind(usersResponseItems.get(position));
        if (activate) {
            holder.binding.imgBtnClear.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgBtnClear.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(usersResponseItems.get(holder.getAdapterPosition())));
        holder.binding.imgBtnLink.setOnClickListener(v -> onItemClickCallback.onLinkClicked(usersResponseItems.get(holder.getAdapterPosition())));
        holder.binding.imgBtnClear.setOnClickListener(v -> onItemClickCallback.onClearClicked(usersResponseItems.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return usersResponseItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListRowUsersBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListRowUsersBinding.bind(itemView);
        }

        public void bind(UsersResponseItem usersResponseItems) {
            Glide.with(itemView).load(usersResponseItems.getAvatarUrl()).placeholder(R.drawable.ic_baseline_account_circle_24).into(binding.imgUsers);
            binding.tvUsername.setText(usersResponseItems.getLogin());
        }
    }
}
