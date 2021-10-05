package me.aprizal.githubusers.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.adapter.OnItemClickCallback;
import me.aprizal.githubusers.adapter.UsersAdapter;
import me.aprizal.githubusers.databinding.ActivityMainBinding;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.viewmodel.MainViewModel;
import me.aprizal.githubusers.viewmodel.SettingPreferences;
import me.aprizal.githubusers.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity implements OnItemClickCallback {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private UsersAdapter adapter;
    private SettingPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        preferences = SettingPreferences.getInstance(dataStore);

        mainViewModel = mainViewModelFactory(MainActivity.this);
        mainViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setListSearchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    setListUsers();
                } else {
                    setListSearchUsers(newText);
                }
                return false;
            }
        });

        setListUsers();

        binding.rvUsers.setHasFixedSize(true);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsersAdapter(this);
        binding.rvUsers.setAdapter(adapter);

        mainViewModel.getShowLoading().observe(this, this::showLoading);
        mainViewModel.getShowToast().observe(this, toast -> Toast.makeText(this, toast, Toast.LENGTH_SHORT).show());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        MenuItem itemFavorite, itemSettings;

        itemFavorite = menu.findItem(R.id.favorite);
        itemFavorite.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            return true;
        });

        itemSettings = menu.findItem(R.id.settings);
        itemSettings.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return false;
        });

        return true;
    }

    private void setListUsers() {
        mainViewModel.setListUsers();
        mainViewModel.getUsersResponseItem().observe(this, usersResponseItems -> {
            if (usersResponseItems !=null){
                adapter.setUsers(usersResponseItems);
                adapter.activateButtons(false);
            }
        });
    }

    private void setListSearchUsers(String login) {
        mainViewModel.setListSearchUser(login);
        mainViewModel.getUsersResponseItem().observe(this, usersResponseItems -> {
            if (usersResponseItems !=null){
                adapter.setUsers(usersResponseItems);
                adapter.activateButtons(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClicked(UsersResponseItem usersResponseItem) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        UsersResponseItem model = new UsersResponseItem();
        model.setLogin(usersResponseItem.getLogin());
        intent.putExtra(DetailActivity.EXTRA_USERS,model);
        startActivity(intent);
    }

    @Override
    public void onLinkClicked(UsersResponseItem usersResponseItem) {
        Toast.makeText(this, getString(R.string.open_link), Toast.LENGTH_SHORT).show();
        String url = usersResponseItem.getHtmlUrl();
        Intent link = new Intent(Intent.ACTION_VIEW);
        link.setData(Uri.parse(url));
        startActivity(link);
    }

    @Override
    public void onClearClicked(UsersResponseItem usersResponseItem) {

    }

    private MainViewModel mainViewModelFactory (AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication(),preferences);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}