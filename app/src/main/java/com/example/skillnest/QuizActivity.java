package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Optional: Uncomment this line once to populate your Firebase Database with questions
        // FirebaseDataPopulator.populateQuestions();

        Button btnStartMobileQuiz = findViewById(R.id.btnStartMobileQuiz);
        btnStartMobileQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizPlayActivity.class);
            intent.putExtra("COURSE_NAME", "Mobile Development");
            startActivity(intent);
        });

        Button btnStartWebQuiz = findViewById(R.id.btnStartWebQuiz);
        btnStartWebQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizPlayActivity.class);
            intent.putExtra("COURSE_NAME", "Web Development");
            startActivity(intent);
        });

        Button btnStartUIUXQuiz = findViewById(R.id.btnStartUIUXQuiz);
        btnStartUIUXQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizPlayActivity.class);
            intent.putExtra("COURSE_NAME", "UI/UX Design");
            startActivity(intent);
        });

        Button btnStartAIMLQuiz = findViewById(R.id.btnStartAIMLQuiz);
        btnStartAIMLQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizPlayActivity.class);
            intent.putExtra("COURSE_NAME", "AI & ML");
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_quiz);
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
                return true;
            } else if (itemId == R.id.progress) {
                startActivity(new Intent(this, ProgressActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
}