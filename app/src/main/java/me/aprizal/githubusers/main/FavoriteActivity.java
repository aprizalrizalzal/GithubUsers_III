package me.aprizal.githubusers.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.adapter.OnItemClickCallback;
import me.aprizal.githubusers.adapter.UsersAdapter;
import me.aprizal.githubusers.databinding.ActivityFavoriteBinding;
import me.aprizal.githubusers.repository.response.UsersResponseItem;
import me.aprizal.githubusers.viewmodel.FavoriteViewModel;
import me.aprizal.githubusers.viewmodel.SettingPreferences;
import me.aprizal.githubusers.viewmodel.ViewModelFactory;

public class FavoriteActivity extends AppCompatActivity implements OnItemClickCallback {

    private ActivityFavoriteBinding binding;
    private FavoriteViewModel favoriteViewModel;
    private UsersAdapter adapter;
    private SettingPreferences preferences;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        builder = new AlertDialog.Builder(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.favorite);
        }

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        preferences = SettingPreferences.getInstance(dataStore);

        binding.rvFavorite.setHasFixedSize(true);
        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsersAdapter(this);
        binding.rvFavorite.setAdapter(adapter);


        favoriteViewModel = favoriteViewModelFactory(FavoriteActivity.this);

        favoriteViewModel.getAllUsersResponseItem().observe(this, usersResponseItems -> {
            if (usersResponseItems !=null){
                adapter.setUsers(usersResponseItems);
            }
        });
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
            startActivity(new Intent(FavoriteActivity.this, SettingsActivity.class));
            return false;
        });

        return true;
    }

    @Override
    public void onItemClicked(UsersResponseItem usersResponseItem) {
        Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
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

    private FavoriteViewModel favoriteViewModelFactory (AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication(),preferences);
        return new ViewModelProvider(activity, factory).get(FavoriteViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onClearClicked(UsersResponseItem usersResponseItem) {

        builder.setMessage(getString(R.string.alert_clear,usersResponseItem.getLogin()))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    Toast.makeText(getApplicationContext(),usersResponseItem.getLogin() + getString(R.string.favorite_clear), Toast.LENGTH_SHORT).show();
                    favoriteViewModel.delete(usersResponseItem);
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setTitle(getString(R.string.delete,usersResponseItem.getLogin()));
        alert.show();
    }
}