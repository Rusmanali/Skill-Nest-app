package com.example.skillnest;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final Map<String, List<String>> categoryLessons = new HashMap<String, List<String>>() {{
        put("Mobile Development", Arrays.asList(
            "Introduction to Android Development",
            "Activities and App Navigation",
            "User Interface Design",
            "RecyclerView and Lists"
        ));
        put("Web Development", Arrays.asList(
            "Introduction to Web Development",
            "HTML Fundamentals",
            "CSS Styling and Responsive Design",
            "JavaScript Basics and DOM Manipulation"
        ));
        put("UI/UX Design", Arrays.asList(
            "Introduction to UI/UX Design",
            "Wireframing and Prototyping",
            "Visual Design and Design Systems",
            "Usability Testing and Design Handoff"
        ));
        put("AI & ML", Arrays.asList(
            "Introduction to AI and Machine Learning",
            "Building Your First ML Model"
        ));
    }};

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
            setupNextButton();
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

    private void setupNextButton() {
        if (btnNext == null || currentCategory == null || currentLessonTitle == null) return;

        List<String> lessons = categoryLessons.get(currentCategory);
        if (lessons == null) {
            // Fallback to quiz if category not found in map
            btnNext.setText("Start Quiz");
            btnNext.setOnClickListener(v -> startQuiz());
            return;
        }

        int currentIndex = lessons.indexOf(currentLessonTitle);
        if (currentIndex != -1 && currentIndex < lessons.size() - 1) {
            // There is a next lesson
            String nextLessonTitle = lessons.get(currentIndex + 1);
            btnNext.setText("Next Lesson");
            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(this, LessonDetailActivity.class);
                intent.putExtra("CATEGORY", currentCategory);
                intent.putExtra("LESSON_TITLE", nextLessonTitle);
                startActivity(intent);
                finish(); // Close current so we don't build a stack
            });
        } else {
            // Last lesson or title not found
            btnNext.setText("Start Quiz");
            btnNext.setOnClickListener(v -> startQuiz());
        }
    }

    private void startQuiz() {
        Intent intent = new Intent(this, QuizPlayActivity.class);
        intent.putExtra("COURSE_NAME", currentCategory);
        startActivity(intent);
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