package com.example.yp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yp.R;
import com.example.yp.databinding.FragmentDashboardBinding;
import com.example.yp.resh1;
import com.example.yp.ui.home.HomeFragment;
import com.example.yp.ui.home.resh2;
import com.example.yp.ui.home.resh3;

import java.util.HashSet;
import java.util.Set;

public class DashboardFragment extends Fragment {
    private LinearLayout favoritesContainer;
    private TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoritesContainer = view.findViewById(R.id.favorites_container);
        emptyText = view.findViewById(R.id.empty_text);

        showFavorites();
    }


    private void showFavorites() {
        favoritesContainer.removeAllViews();
        SharedPreferences prefs = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet("favorites", new HashSet<>());

        if(favorites.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            return;
        }

        emptyText.setVisibility(View.GONE);

        for(String recipeId : favorites) {
            String title = prefs.getString(recipeId, "Рецепт");
            addRecipeItem(recipeId, title);
        }
    }

    private void addRecipeItem(String recipeId, String title) {
        TextView textView = new TextView(requireContext());
        textView.setText(title);
        textView.setTextSize(18);
        textView.setPadding(0, 16, 0, 16);
        textView.setOnClickListener(v -> openRecipe(recipeId));

        // Добавляем разделитель
        View divider = new View(requireContext());
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2
        ));
        divider.setBackgroundColor(Color.GRAY);

        favoritesContainer.addView(textView);
        favoritesContainer.addView(divider);
    }

    private void openRecipe(String recipeId) {
        Intent intent;
        switch (recipeId) {
            case "recipe_1":
                intent = new Intent(requireContext(), resh1.class);
                break;
            case "recipe_2":
                intent = new Intent(requireContext(), resh2.class);
                break;
            case "recipe_3":
                intent = new Intent(requireContext(), resh3.class);
                break;
            case "recipe_4":
                intent = new Intent(requireContext(), resh3.class);
                break;
            case "recipe_5":
                intent = new Intent(requireContext(), resh3.class);
                break;
            case "recipe_6":
                intent = new Intent(requireContext(), resh3.class);
                break;
            default:
                intent = new Intent(requireContext(), HomeFragment.class);
                break;
        }
        intent.putExtra("RECIPE_ID", recipeId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        showFavorites();
    }

}
