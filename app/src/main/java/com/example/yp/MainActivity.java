package com.example.yp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yp.ui.notifications.SoundClick;

public class MainActivity extends AppCompatActivity {
private TextView Registration;
private EditText Email, Password;
private Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Registration = findViewById(R.id.Registration);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        button = findViewById(R.id.button);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        button.setOnClickListener(v -> {
            SoundClick.soundClick(this);
            startActivity(new Intent(this, MainMenu.class));
            finish();
        });
        Registration.setOnClickListener(v -> {
            SoundClick.soundClick(this);
            startActivity(new Intent(this, Registration.class));
            finish();
        });
        button.setOnClickListener(v -> {
            SoundClick.soundClick(this);
            String sEmail = Email.getText().toString();
            String sPassword = Password.getText().toString();

            if (sEmail.isEmpty() || sPassword.isEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                return;
            }

            if (databaseHelper.checkUser(sEmail, sPassword)) {
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                prefs.edit().putBoolean("isLoggedIn", true).putString("email", sEmail).apply();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Войти не получилось. Попробуйте зарегистрироваться", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
