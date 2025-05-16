package com.example.yp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class resh1 extends AppCompatActivity {
    private String currentRecipeId;
    private String currentRecipeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resh1);

        currentRecipeId = "recipe_1";
        currentRecipeTitle = "Простой завтрак из картофеля с яичной заливкой";

        ImageButton favoriteButton = findViewById(R.id.favorite_button);
        updateButton(favoriteButton);

        favoriteButton.setOnClickListener(v -> {
            toggleFavorite();
            updateButton(favoriteButton);
        });
    }

    private void toggleFavorite() {
        SharedPreferences prefs = getSharedPreferences("Favorites", MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(prefs.getStringSet("favorites", new HashSet<>()));

        if(favorites.contains(currentRecipeId)) {
            favorites.remove(currentRecipeId);
        } else {
            favorites.add(currentRecipeId);
            // Сохраняем название
            prefs.edit().putString(currentRecipeId, currentRecipeTitle).apply();
        }

        prefs.edit().putStringSet("favorites", favorites).apply();
    }

    private void updateButton(ImageButton btn) {
        SharedPreferences prefs = getSharedPreferences("Favorites", MODE_PRIVATE);
        boolean isFav = prefs.getStringSet("favorites", new HashSet<>()).contains(currentRecipeId);
        btn.setImageResource(isFav ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
    }
}
