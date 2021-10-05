package me.aprizal.githubusers.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import me.aprizal.githubusers.R;
import me.aprizal.githubusers.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends Activity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(binding.imgIcon);
        binding.tvAppName.setText(R.string.app_name);

        Thread thread = new Thread(() -> {
            long splash = 1500;
            try {
                Thread.sleep(splash);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}