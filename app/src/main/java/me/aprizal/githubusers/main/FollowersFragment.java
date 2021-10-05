package me.aprizal.githubusers.main;

import static me.aprizal.githubusers.main.DetailActivity.EXTRA_USERS;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.adapter.OnItemClickCallback;
import me.aprizal.githubusers.adapter.UsersAdapter;
import me.aprizal.githubusers.databinding.FragmentFollowersBinding;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.viewmodel.FollowersViewModel;

public class FollowersFragment extends Fragment  implements OnItemClickCallback {

    private UsersAdapter adapter;
    private FragmentFollowersBinding binding;

    public FollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        UsersResponseItem usersResponseItem = requireActivity().getIntent().getParcelableExtra(EXTRA_USERS);

        binding.rvFollower.setHasFixedSize(true);
        binding.rvFollower.setLayoutManager(new LinearLayoutManager(requireActivity()));

        adapter = new UsersAdapter(this);
        binding.rvFollower.setAdapter(adapter);

        FollowersViewModel followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);
        String login = usersResponseItem.getLogin();

        followersViewModel.setUser(login);
        followersViewModel.getUsersFollowersItems().observe(requireActivity(), usersResponseItems -> {
            if (usersResponseItems !=null){
                adapter.setUsers(usersResponseItems);
                adapter.activateButtons(false);
            }
        });

        followersViewModel.getShowLoading().observe(requireActivity(), this::showLoading);
        followersViewModel.getShowToast().observe(requireActivity(), toast -> Toast.makeText(requireActivity(), toast, Toast.LENGTH_SHORT).show());

    }

    private void showLoading(Boolean state) {
        if (state) {
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClicked(UsersResponseItem usersResponseItem) {
        Intent intent = new Intent(requireActivity(), DetailActivity.class);
        UsersResponseItem model = new UsersResponseItem();
        model.setLogin(usersResponseItem.getLogin());
        intent.putExtra(EXTRA_USERS,model);
        startActivity(intent);
    }

    @Override
    public void onLinkClicked(UsersResponseItem usersResponseItem) {
        Toast.makeText(requireActivity(), getString(R.string.open_link), Toast.LENGTH_SHORT).show();
        String url = usersResponseItem.getHtmlUrl();
        Intent link = new Intent(Intent.ACTION_VIEW);
        link.setData(Uri.parse(url));
        startActivity(link);
    }

    @Override
    public void onClearClicked(UsersResponseItem usersResponseItem) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}