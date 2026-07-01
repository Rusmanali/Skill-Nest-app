package com.example.skillnest;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LessonDetailActivity extends AppCompatActivity {

    private static final String TAG = "LessonDetailActivity";

    private boolean isMarkedDone = false;
    private TextView tvLessonNumber, tvOverviewText, tvLearningPoints, tvProgressPercent, tvTitle;
    private Button btnNext, btnMarkDone;

    private String currentLessonTitle;
    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);

        TextView tvCategory = findViewById(R.id.tvCourseCategory);
        tvTitle = findViewById(R.id.tvLessonTitle);
        tvLessonNumber = findViewById(R.id.tvLessonNumber);
        tvOverviewText = findViewById(R.id.tvOverviewText);
        tvLearningPoints = findViewById(R.id.tvLearningPoints);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        
        Button btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnMarkDone = findViewById(R.id.btnMarkDone);

        resetMarkDoneButton();

        currentCategory = getIntent().getStringExtra("CATEGORY");
        currentLessonTitle = getIntent().getStringExtra("LESSON_TITLE");
        
        if (currentCategory != null && tvCategory != null) {
            tvCategory.setText(currentCategory);
        }
        if (currentLessonTitle != null) {
            if (tvTitle != null) tvTitle.setText(currentLessonTitle);
            fetchLessonData(currentCategory, currentLessonTitle);
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
            });
        }

        if (btnMarkDone != null) {
            btnMarkDone.setOnClickListener(v -> {
                isMarkedDone = !isMarkedDone;
                if (isMarkedDone) {
                    btnMarkDone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#39C782")));
                    btnMarkDone.setTextColor(Color.WHITE);
                } else {
                    btnMarkDone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D9D9D9")));
                    btnMarkDone.setTextColor(Color.BLACK);
                }
            });
        }
    }

    private void fetchLessonData(String category, String title) {
        if (category == null || title == null) return;
        
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("lessons")
                    .child(category.replace(" ", "_"))
                    .child(title.replace(" ", "_"));
                    
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        Lesson lesson = snapshot.getValue(Lesson.class);
                        if (lesson != null) {
                            if (tvLessonNumber != null) tvLessonNumber.setText(lesson.getLessonNumber() + ":");
                            if (tvOverviewText != null) tvOverviewText.setText(lesson.getOverview());
                            if (tvLearningPoints != null) tvLearningPoints.setText(lesson.getLearningPoints());
                            if (tvProgressPercent != null) tvProgressPercent.setText(lesson.getProgress());
                            
                            if (btnNext != null) {
                                if ("100%".equals(lesson.getProgress())) {
                                    btnNext.setText("Finish");
                                } else {
                                    btnNext.setText("Next");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing lesson: " + e.getMessage());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LessonDetailActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Firebase Error", e);
        }
    }

    private void resetMarkDoneButton() {
        if (btnMarkDone != null) {
            btnMarkDone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D9D9D9")));
            btnMarkDone.setTextColor(Color.BLACK);
            isMarkedDone = false;
        }
    }
}