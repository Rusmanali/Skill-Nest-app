package com.example.skillnest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Handle the splash screen transition.
        SplashScreen.installSplashScreen(this);

        // One-time Firebase Data Population
        FirebaseDataPopulator.populateQuestions();
        FirebaseDataPopulator.populateLessons();

        super.onCreate(savedInstanceState);
        
        // Step 1: Show first splash screen
        setContentView(R.layout.splash_screen1);

        // Step 2: Transition to second splash screen after 1.5 seconds
        new android.os.Handler().postDelayed(() -> {
            setContentView(R.layout.splash_screen2);
            
            View logo = findViewById(R.id.logo_splash);
            View name = findViewById(R.id.imageView4);
            
            if (logo != null && name != null) {
                // Initial state: Logo centered, name hidden
                name.setAlpha(0f);
                
                // Calculate offset to center the logo. 
                // LinearLayout width is 324dp, Logo is 120dp at the start.
                // Center of LinearLayout = 162dp. Center of Logo = 60dp.
                // Shift logo by 102dp to the right to center it.
                float density = getResources().getDisplayMetrics().density;
                logo.setTranslationX(102 * density);
                
                // Animate logo to its side position and fade in the name
                logo.animate()
                    .translationX(0)
                    .setDuration(800)
                    .setInterpolator(new android.view.animation.DecelerateInterpolator())
                    .start();
                    
                name.animate()
                    .alpha(1f)
                    .setDuration(800)
                    .setStartDelay(400)
                    .start();
            }
            
            // Step 3: Transition to onboarding after another 2.5 seconds
            new android.os.Handler().postDelayed(this::setupOnboarding, 2500);
        }, 1500);
    }

    private void setupOnboarding() {
        setContentView(R.layout.onboarding1);

        // Smart entrance animation for onboarding views
        View illustration = findViewById(R.id.imageView);
        View title = findViewById(R.id.textView);
        View desc = findViewById(R.id.textView2);
        View nextBtn = findViewById(R.id.imageButton);

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

        ImageButton nextButton = (ImageButton) nextBtn;
        if (nextButton != null) {
            nextButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, Onboarding2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
    }
}