package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProgressActivity extends AppCompatActivity {

    private TextView tvCompletedCount, tvAvgScore;
    private TextView tvMobileScore, tvWebScore, tvUIUXScore, tvAIMLScore;
    private ProgressBar pbMobile, pbWeb, pbUIUX, pbAIML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        tvCompletedCount = findViewById(R.id.tvCompletedCount);
        tvAvgScore = findViewById(R.id.tvAvgScore);
        
        tvMobileScore = findViewById(R.id.tvMobileScore);
        tvWebScore = findViewById(R.id.tvWebScore);
        tvUIUXScore = findViewById(R.id.tvUIUXScore);
        tvAIMLScore = findViewById(R.id.tvAIMLScore);
        
        pbMobile = findViewById(R.id.pbMobile);
        pbWeb = findViewById(R.id.pbWeb);
        pbUIUX = findViewById(R.id.pbUIUX);
        pbAIML = findViewById(R.id.pbAIML);

        fetchUserProgress();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.progress);
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
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void fetchUserProgress() {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("scores").child("GuestUser");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int totalCompleted = 0;
                    int totalPoints = 0;
                    int maxPossiblePoints = 0;

                    for (DataSnapshot data : snapshot.getChildren()) {
                        Score s = data.getValue(Score.class);
                        if (s != null) {
                            totalCompleted++;
                            totalPoints += s.getScore();
                            maxPossiblePoints += s.getTotal();
                            
                            updateCourseUI(s);
                        }
                    }

                    if (tvCompletedCount != null) tvCompletedCount.setText(String.valueOf(totalCompleted));
                    if (maxPossiblePoints > 0 && tvAvgScore != null) {
                        int avg = (totalPoints * 100) / maxPossiblePoints;
                        tvAvgScore.setText(getString(R.string.avg_score_format, avg));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCourseUI(Score s) {
        if (s == null || s.getTotal() <= 0) return;
        
        String name = s.getCourseName();
        int percent = (s.getScore() * 100) / s.getTotal();
        String scoreText = getString(R.string.progress_format, s.getScore(), s.getTotal(), percent);

        if ("Mobile Development".equals(name)) {
            if (pbMobile != null) pbMobile.setProgress(percent);
            if (tvMobileScore != null) tvMobileScore.setText(scoreText);
        } else if ("Web Development".equals(name)) {
            if (pbWeb != null) pbWeb.setProgress(percent);
            if (tvWebScore != null) tvWebScore.setText(scoreText);
        } else if ("UI/UX Design".equals(name)) {
            if (pbUIUX != null) pbUIUX.setProgress(percent);
            if (tvUIUXScore != null) tvUIUXScore.setText(scoreText);
        } else if ("AI & ML".equals(name) || "AI & Machine Learning".equals(name)) {
            if (pbAIML != null) pbAIML.setProgress(percent);
            if (tvAIMLScore != null) tvAIMLScore.setText(scoreText);
        }
    }
}