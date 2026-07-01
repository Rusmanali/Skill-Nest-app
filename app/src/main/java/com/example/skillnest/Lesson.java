package com.example.skillnest;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Lesson {
    public String category;
    public String title;
    public String lessonNumber;
    public String overview;
    public String learningPoints;
    public String progress;

    public Lesson() {
        // Required for Firebase
    }

    public Lesson(String category, String title, String lessonNumber, String overview, String learningPoints, String progress) {
        this.category = category;
        this.title = title;
        this.lessonNumber = lessonNumber;
        this.overview = overview;
        this.learningPoints = learningPoints;
        this.progress = progress;
    }

    public String getCategory() { return category != null ? category : ""; }
    public String getTitle() { return title != null ? title : ""; }
    public String getLessonNumber() { return lessonNumber != null ? lessonNumber : ""; }
    public String getOverview() { return overview != null ? overview : ""; }
    public String getLearningPoints() { return learningPoints != null ? learningPoints : ""; }
    public String getProgress() { return progress != null ? progress : ""; }

    public void setCategory(String category) { this.category = category; }
    public void setTitle(String title) { this.title = title; }
    public void setLessonNumber(String lessonNumber) { this.lessonNumber = lessonNumber; }
    public void setOverview(String overview) { this.overview = overview; }
    public void setLearningPoints(String learningPoints) { this.learningPoints = learningPoints; }
    public void setProgress(String progress) { this.progress = progress; }
}