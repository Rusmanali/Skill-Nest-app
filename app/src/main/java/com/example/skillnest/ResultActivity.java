package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvCourseName = findViewById(R.id.tvCourseName);
        TextView tvScore = findViewById(R.id.tvScore);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);

        String courseName = getIntent().getStringExtra("COURSE_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 10);

        tvCourseName.setText(courseName);
        tvScore.setText(getString(R.string.score_format, score, total));

        // Save score to Firebase
        saveScoreToFirebase(courseName, score, total);

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void saveScoreToFirebase(String courseName, int score, int total) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("scores").child("GuestUser");
        Score scoreObj = new Score(courseName, score, total);
        ref.child(courseName.replace(" ", "_")).setValue(scoreObj);
    }
}
