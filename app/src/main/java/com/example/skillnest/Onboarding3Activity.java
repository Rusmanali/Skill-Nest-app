package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Onboarding3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding3);

        // Smart entrance animation
        View illustration = findViewById(R.id.imageView);
        View title = findViewById(R.id.textView);
        View desc = findViewById(R.id.textView2);

        if (illustration != null) {
            illustration.setAlpha(0f);
            illustration.setTranslationY(50f);
            illustration.animate().alpha(1f).translationY(0f).setDuration(600).start();
        }
        if (title != null) {
            title.setAlpha(0f);
            title.setTranslationY(30f);
            title.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(200).start();
        }
        if (desc != null) {
            desc.setAlpha(0f);
            desc.setTranslationY(30f);
            desc.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(400).start();
        }

        ImageButton nextButton = findViewById(R.id.imageButton);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Onboarding3Activity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}