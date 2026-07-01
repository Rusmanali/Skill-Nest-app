package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ImageButton btnLearn1 = findViewById(R.id.imageButton3); // Mobile
        ImageButton btnLearn2 = findViewById(R.id.imageButton2); // Websites
        ImageButton btnLearn3 = findViewById(R.id.imageButton4); // UI/UX
        ImageButton btnLearn4 = findViewById(R.id.imageButton5); // AI/ML

        if (btnLearn1 != null) {
            btnLearn1.setOnClickListener(v -> navigateToLessons("Mobile"));
        }
        if (btnLearn2 != null) {
            btnLearn2.setOnClickListener(v -> navigateToLessons("Websites"));
        }
        if (btnLearn3 != null) {
            btnLearn3.setOnClickListener(v -> navigateToLessons("UIUX"));
        }
        if (btnLearn4 != null) {
            btnLearn4.setOnClickListener(v -> navigateToLessons("AIML"));
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                return true;
            } else if (itemId == R.id.navigation_courses) {
                startActivity(new Intent(this, LessonsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_quiz) {
                startActivity(new Intent(this, QuizActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.progress) {
                startActivity(new Intent(this, ProgressActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void navigateToLessons(String category) {
        Intent intent = new Intent(HomeActivity.this, LessonsActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}