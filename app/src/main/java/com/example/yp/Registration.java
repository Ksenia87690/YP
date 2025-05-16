package com.example.yp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Registration extends AppCompatActivity {
    EditText name, email, password;
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.editTextText);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        button = findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Registration.this, MainActivity.class);
            startActivity(intent);
        });
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        button.setOnClickListener(v -> {
            SoundClick.soundClick(this);
            String sName = name.getText().toString();
            String sEmail = email.getText().toString();
            String sPassword = password.getText().toString();

            if (sName.isEmpty() || sEmail.isEmpty() ||  sPassword.isEmpty()){
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                return;
            }


            boolean inserted = databaseHelper.insertUser(sName, sEmail,sPassword);
            if (inserted) {
                Toast.makeText(this, "Рад знакомству, " + sName, Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                prefs.edit().putBoolean("isLoggedIn", true).putString("email", sEmail).apply();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(this, "Попытка регистрации не удалась. Попробуйте снова", Toast.LENGTH_SHORT).show();
            }
        });
    }
}