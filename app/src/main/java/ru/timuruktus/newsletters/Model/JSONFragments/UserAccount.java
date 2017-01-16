package ru.timuruktus.newsletters.Model.JSONFragments;


import android.util.Log;

public class UserAccount {

    public final static String TAG = "tag";

    String username;
    int rating = 0;

    public UserAccount(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }






}
