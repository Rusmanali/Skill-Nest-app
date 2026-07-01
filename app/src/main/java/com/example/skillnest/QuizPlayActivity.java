package com.example.skillnest;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizPlayActivity extends AppCompatActivity {

    private static final String TAG = "QuizPlayActivity";

    private TextView tvQuestionNumber, tvQuestionText, tvCourseTitle;
    private MaterialButton btnOptA, btnOptB, btnOptC, btnOptD;
    private Button btnBack, btnNext;

    private List<Question> questionList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int selectedOptionIndex = -1;
    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_play);

        // Initialize Views
        tvCourseTitle = findViewById(R.id.tvCourseTitle);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestionText = findViewById(R.id.tvQuestionText);
        btnOptA = findViewById(R.id.btnOptA);
        btnOptB = findViewById(R.id.btnOptB);
        btnOptC = findViewById(R.id.btnOptC);
        btnOptD = findViewById(R.id.btnOptD);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        
        courseName = getIntent().getStringExtra("COURSE_NAME");
        if (courseName != null) tvCourseTitle.setText(courseName);

        fetchQuestions();

        // Option Button Listeners
        if (btnOptA != null) btnOptA.setOnClickListener(v -> selectOption(0, btnOptA));
        if (btnOptB != null) btnOptB.setOnClickListener(v -> selectOption(1, btnOptB));
        if (btnOptC != null) btnOptC.setOnClickListener(v -> selectOption(2, btnOptC));
        if (btnOptD != null) btnOptD.setOnClickListener(v -> selectOption(3, btnOptD));

        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                if (questionList.isEmpty()) return;
                
                if (selectedOptionIndex == -1) {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if correct
                if (selectedOptionIndex == questionList.get(currentQuestionIndex).getCorrectOptionIndex()) {
                    score++;
                }

                if (currentQuestionIndex < questionList.size() - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                } else {
                    showResult();
                }
            });
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void fetchQuestions() {
        if (courseName == null) {
            Toast.makeText(this, "Error: Course name not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("quizzes").child(courseName);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    questionList.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        try {
                            Question q = data.getValue(Question.class);
                            if (q != null) questionList.add(q);
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing question: " + e.getMessage());
                        }
                    }
                    
                    if (!questionList.isEmpty()) {
                        loadQuestion();
                    } else {
                        Toast.makeText(QuizPlayActivity.this, "No questions found for " + courseName, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(QuizPlayActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Firebase Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Firebase Initialization Error", e);
            finish();
        }
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questionList.size()) return;

        Question q = questionList.get(currentQuestionIndex);
        if (q == null) return;

        String qNumText = "Question " + (currentQuestionIndex + 1) + "/" + questionList.size();
        if (tvQuestionNumber != null) tvQuestionNumber.setText(qNumText);
        if (tvQuestionText != null) tvQuestionText.setText(q.getQuestionText());
        
        List<String> opts = q.getOptions();
        if (opts != null && opts.size() >= 4) {
            if (btnOptA != null) btnOptA.setText("A. " + opts.get(0));
            if (btnOptB != null) btnOptB.setText("B. " + opts.get(1));
            if (btnOptC != null) btnOptC.setText("C. " + opts.get(2));
            if (btnOptD != null) btnOptD.setText("D. " + opts.get(3));
        }

        resetOptionButtons();
        selectedOptionIndex = -1;
        
        if (btnNext != null) {
            if (currentQuestionIndex == questionList.size() - 1) {
                btnNext.setText("Finish");
            } else {
                btnNext.setText("Next");
            }
        }
    }

    private void selectOption(int index, MaterialButton btn) {
        if (btn == null) return;
        resetOptionButtons();
        selectedOptionIndex = index;
        btn.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.brand_green)));
        btn.setStrokeWidth(4);
        btn.setTextColor(ContextCompat.getColor(this, R.color.brand_green));
    }

    private void resetOptionButtons() {
        ColorStateList grey = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey_light));
        int black = ContextCompat.getColor(this, R.color.black);
        
        MaterialButton[] buttons = {btnOptA, btnOptB, btnOptC, btnOptD};
        for (MaterialButton btn : buttons) {
            if (btn != null) {
                btn.setStrokeColor(grey);
                btn.setStrokeWidth(2);
                btn.setTextColor(black);
            }
        }
    }

    private void showResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("COURSE_NAME", courseName);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL", questionList.size());
        startActivity(intent);
        finish();
    }
}