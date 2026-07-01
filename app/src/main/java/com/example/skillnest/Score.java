package com.example.skillnest;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Score {
    public String courseName;
    public int score;
    public int total;

    public Score() {}

    public Score(String courseName, int score, int total) {
        this.courseName = courseName;
        this.score = score;
        this.total = total;
    }

    public String getCourseName() { return courseName != null ? courseName : ""; }
    public int getScore() { return score; }
    public int getTotal() { return total; }

    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setScore(int score) { this.score = score; }
    public void setTotal(int total) { this.total = total; }
}