package com.example.yp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences prefs, sharedPreferences;
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String KEY_THEME = "isDarkTheme";
//    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Задержка в 3 секунды (3000 миллисекунд)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

                // Пользователь уже вошёл, переходим на MainActivity
                // Пользователь не вошёл, переходим на LoginActivity
                if (isLoggedIn) {
 //                   animationView.pauseAnimation();
                    startActivity(new Intent(SplashActivity.this, MainMenu.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, Registration.class));
                }
                finish();

            }
        }, 6000);
        VideoView videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.uiiai));
        videoView.start();
    }
}
