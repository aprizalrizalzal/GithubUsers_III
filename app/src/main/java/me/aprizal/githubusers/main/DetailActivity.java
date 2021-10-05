package me.aprizal.githubusers.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.adapter.SectionsPagerAdapter;
import me.aprizal.githubusers.databinding.ActivityDetailBinding;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.viewmodel.DetailViewModel;
import me.aprizal.githubusers.viewmodel.SettingPreferences;
import me.aprizal.githubusers.viewmodel.ViewModelFactory;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private SettingPreferences preferences;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.followers,
            R.string.following
    };

    public static final String EXTRA_USERS = "extra_users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        preferences = SettingPreferences.getInstance(dataStore);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();

        UsersResponseItem usersResponseItem = getIntent().getParcelableExtra(EXTRA_USERS);
        String login = usersResponseItem.getLogin();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(login);
        }

        DetailViewModel detailViewModel = detailViewModelFactory(DetailActivity.this);

        detailViewModel.setDetailUser(login);
        detailViewModel.getDetailUsersResponseItem().observe(this, item -> {

            Glide.with(DetailActivity.this).load(item.getAvatarUrl()).centerCrop().placeholder(R.drawable.ic_baseline_account_circle_24).into(binding.imgAvatar);
            binding.tvName.setText(item.getName());
            binding.tvUsername.setText(item.getLogin());
            binding.tvCompany.setText(item.getCompany());
            binding.tvLocation.setText(item.getLocation());
            binding.tvRepository.setText(String.valueOf(item.getPublicRepos()));

            binding.fabFavorite.setOnClickListener(v -> {
                detailViewModel.insert(item);
                Toast.makeText(this, item.getLogin() + getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();
            });
        });

        detailViewModel.getShowLoading().observe(this, this::showLoading);
        detailViewModel.getShowToast().observe(this, toast -> Toast.makeText(this, toast, Toast.LENGTH_SHORT).show());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        MenuItem itemFavorite, itemSettings;

        itemFavorite = menu.findItem(R.id.favorite);
        itemFavorite.setVisible(false);

        itemSettings = menu.findItem(R.id.settings);
        itemSettings.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(DetailActivity.this, SettingsActivity.class));
            return false;
        });

        return true;
    }

    private void showLoading(Boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private DetailViewModel detailViewModelFactory (AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication(),preferences);
        return new ViewModelProvider(activity, factory).get(DetailViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

