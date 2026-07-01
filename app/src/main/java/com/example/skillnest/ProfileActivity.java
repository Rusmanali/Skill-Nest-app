package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_courses) {
                startActivity(new Intent(this, LessonsActivity.class));
                finish();
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
                return true;
            }
            return false;
        });
    }
}