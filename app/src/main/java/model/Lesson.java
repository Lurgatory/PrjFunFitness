package model;

import androidx.annotation.NonNull;

public class Lesson {

    String title, content, authorName, date;

    public Lesson() {
    }

    public Lesson(String title, String content, String authorName, String date) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}


