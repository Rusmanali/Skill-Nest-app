package com.example.skillnest;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LessonsActivity extends AppCompatActivity {

    private LinearLayout lessonsMobile, lessonsWebsites, lessonsUIUX, lessonsAIML;
    private Button btnMobile, btnWebsites, btnUIUX, btnAIML;

    private String currentSelectedTab = "Mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        // Initialize Layouts
        lessonsMobile = findViewById(R.id.lessonsMobile);
        lessonsWebsites = findViewById(R.id.lessonsWebsites);
        lessonsUIUX = findViewById(R.id.lessonsUIUX);
        lessonsAIML = findViewById(R.id.lessonsAIML);

        // Initialize Buttons
        btnMobile = findViewById(R.id.btnMobile);
        btnWebsites = findViewById(R.id.btnWebsites);
        btnUIUX = findViewById(R.id.btnUIUX);
        btnAIML = findViewById(R.id.btnAIML);

        // Set Click Listeners
        btnMobile.setOnClickListener(v -> selectTab("Mobile"));
        btnWebsites.setOnClickListener(v -> selectTab("Websites"));
        btnUIUX.setOnClickListener(v -> selectTab("UIUX"));
        btnAIML.setOnClickListener(v -> selectTab("AIML"));

        // Check for category from intent
        String category = getIntent().getStringExtra("CATEGORY");
        if (category != null) {
            selectTab(category);
        } else {
            selectTab("Mobile"); // Default
        }

        setupLearnButtons();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_courses);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_courses) {
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
                finish();
                return true;
            }
            return false;
        });
    }

    private void selectTab(String tab) {
        currentSelectedTab = tab;
        // Reset all buttons
        resetButton(btnMobile);
        resetButton(btnWebsites);
        resetButton(btnUIUX);
        resetButton(btnAIML);

        // Hide all lesson lists
        lessonsMobile.setVisibility(View.GONE);
        lessonsWebsites.setVisibility(View.GONE);
        lessonsUIUX.setVisibility(View.GONE);
        lessonsAIML.setVisibility(View.GONE);

        // Highlight selected button and show its list
        switch (tab) {
            case "Mobile":
                highlightButton(btnMobile);
                lessonsMobile.setVisibility(View.VISIBLE);
                break;
            case "Websites":
                highlightButton(btnWebsites);
                lessonsWebsites.setVisibility(View.VISIBLE);
                break;
            case "UIUX":
                highlightButton(btnUIUX);
                lessonsUIUX.setVisibility(View.VISIBLE);
                break;
            case "AIML":
                highlightButton(btnAIML);
                lessonsAIML.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetButton(Button btn) {
        btn.setBackgroundResource(R.drawable.rounded_white);
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        btn.setTextColor(Color.BLACK);
    }

    private void highlightButton(Button btn) {
        btn.setBackgroundResource(R.drawable.button_pill);
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#39C782")));
        btn.setTextColor(Color.WHITE);
    }

    private void setupLearnButtons() {
        // Since I have many buttons, I'll find them in each container
        setListenersInContainer(lessonsMobile, "Mobile");
        setListenersInContainer(lessonsWebsites, "Websites");
        setListenersInContainer(lessonsUIUX, "UIUX");
        setListenersInContainer(lessonsAIML, "AIML");
    }

    private void setListenersInContainer(LinearLayout container, String tab) {
        if (container == null) return;
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof androidx.constraintlayout.widget.ConstraintLayout) {
                androidx.constraintlayout.widget.ConstraintLayout layout = (androidx.constraintlayout.widget.ConstraintLayout) child;
                
                String title = "";
                Button learnBtn = null;

                for (int j = 0; j < layout.getChildCount(); j++) {
                    View innerChild = layout.getChildAt(j);
                    if (innerChild instanceof com.google.android.material.card.MaterialCardView) {
                        com.google.android.material.card.MaterialCardView card = (com.google.android.material.card.MaterialCardView) innerChild;
                        // Card contains a LinearLayout
                        if (card.getChildCount() > 0 && card.getChildAt(0) instanceof LinearLayout) {
                            LinearLayout textContainer = (LinearLayout) card.getChildAt(0);
                            if (textContainer.getChildCount() >= 2 && textContainer.getChildAt(1) instanceof TextView) {
                                title = ((TextView) textContainer.getChildAt(1)).getText().toString();
                            }
                        }
                    } else if (innerChild instanceof Button) {
                        learnBtn = (Button) innerChild;
                    }
                }

                if (learnBtn != null) {
                    final String lessonTitle = title;
                    final String currentCategory = tab; // Use the tab variable from selectTab if possible, or category variable
                    learnBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#39C782")));
                    learnBtn.setOnClickListener(v -> {
                        Intent intent = new Intent(this, LessonDetailActivity.class);
                        intent.putExtra("LESSON_TITLE", lessonTitle);
                        // We need to pass the category name
                        String categoryDisplayName = "Mobile Development";
                        if ("Websites".equals(tab)) categoryDisplayName = "Web Development";
                        else if ("UIUX".equals(tab)) categoryDisplayName = "UI/UX Design";
                        else if ("AIML".equals(tab)) categoryDisplayName = "AI & ML";
                        
                        intent.putExtra("CATEGORY", categoryDisplayName);
                        startActivity(intent);
                    });
                }
            }
        }
    }
}