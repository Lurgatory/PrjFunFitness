package model;

import androidx.annotation.NonNull;

public class Workout {
	//fields
    String workoutName, repetition, set;
    //default constructor
    public Workout() {
    }
    //constructor w/ 3 params
    public Workout(String strWorkoutName, String strRepetition, String strSet) {
        this.workoutName = strWorkoutName;
        this.repetition = strRepetition;
        this.set = strSet;
    }
   //geters/setters
    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
   //toString
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
