package Models;

import java.sql.Timestamp;

public class Feedback {
    private int feedBackID;
    private int userID;
    private int rating;
    private String feedBack;
    private Timestamp time;

    public Feedback(int feedbackID, int userID, String feedback_message, int rating, Timestamp time) {
        this.feedBackID = feedbackID;
        this.userID = userID;
        this.feedBack = feedback_message;
        this.rating = rating;
        this.time = time;
    }

    public Feedback(int userID, String feedback_message, int rating, Timestamp time) {
        this.userID = userID;
        this.feedBack = feedback_message;
        this.rating = rating;
        this.time = time;
    }

    public int getFeedBackID() {
        return feedBackID;
    }

    public void setFeedBackID(int feedBackID) {
        this.feedBackID = feedBackID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

}