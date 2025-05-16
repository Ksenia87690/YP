package com.example.yp.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.yp.R;
import com.example.yp.databinding.FragmentHomeBinding;
import com.example.yp.resh1;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ImageView profileIcon;
    private LinearLayout recipe1, recipe2, recipe3;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Инициализация ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Инициализация View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Инициализация элементов
        profileIcon = binding.imageView7; // Убедитесь, что ID совпадает с XML
        recipe1 = binding.recipe1;
        recipe2 = binding.recipe2;
        recipe3 = binding.recipe3;

        // Обработчики кликов
        setupClickListeners();

        return root;
    }

    private void setupClickListeners() {
        // Обработчик для профиля с анимацией
        profileIcon.setOnClickListener(v -> openProfileActivity());

        // Обработчики для рецептов
        recipe1.setOnClickListener(v -> startActivity(new Intent(getActivity(), resh1.class)));
        recipe2.setOnClickListener(v -> startActivity(new Intent(getActivity(), resh2.class)));
        recipe3.setOnClickListener(v -> startActivity(new Intent(getActivity(), resh3.class)));
    }

    private void openProfileActivity() {
        Intent intent = new Intent(getActivity(), Profile.class);

        // Анимация перехода для Android 5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(),
                    profileIcon,
                    "profile_transition"
            );
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

        // Дополнительная анимация масштабирования
        if (getActivity() != null) {
            getActivity().overridePendingTransition(R.anim.scale_up, R.anim.no_animation);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
