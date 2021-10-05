package me.aprizal.githubusers.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.databinding.ActivitySettingsBinding;
import me.aprizal.githubusers.viewmodel.SettingPreferences;
import me.aprizal.githubusers.viewmodel.SettingsViewModel;
import me.aprizal.githubusers.viewmodel.ViewModelFactory;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private SettingsViewModel settingsViewModel;
    private SettingPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.settings);
        }

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        preferences = SettingPreferences.getInstance(dataStore);

        settingsViewModel = settingsViewModelFactory(this);
        settingsViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                binding.switchTheme.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                binding.switchTheme.setChecked(false);
            }
        });

        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> settingsViewModel.saveThemeSetting(isChecked));

        binding.imgBtnLanguage.setOnClickListener(v -> {
            Intent language = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(language);
        });
    }

    private SettingsViewModel settingsViewModelFactory (AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication(),preferences);
        return new ViewModelProvider(activity, factory).get(SettingsViewModel.class);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}