package me.aprizal.githubusers.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final SettingPreferences preferences;

    public SettingsViewModel(SettingPreferences preferences) {
        this.preferences = preferences;
    }

    public LiveData<Boolean> getThemeSettings() {
        return LiveDataReactiveStreams.fromPublisher(preferences.getThemeSetting());
    }

    public void saveThemeSetting(Boolean isDarkModeActive) {
        preferences.saveThemeSetting(isDarkModeActive);
    }
}
