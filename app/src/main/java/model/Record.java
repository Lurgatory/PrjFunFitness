package model;

import androidx.annotation.NonNull;

public class Record {
    String date;
    Body body;
    Meal meal;

    public Record(String date, Body body, Meal meal) {
        this.date = date;
        this.body = body;
        this.meal = meal;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
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


