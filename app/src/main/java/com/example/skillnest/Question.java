package com.example.skillnest;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Question {
    public String questionText;
    public List<String> options;
    public int correctOptionIndex;

    public Question() {
        // Required for Firebase
        this.options = new ArrayList<>();
    }

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setOptions(List<String> options) { this.options = options; }
    public void setCorrectOptionIndex(int correctOptionIndex) { this.correctOptionIndex = correctOptionIndex; }
}