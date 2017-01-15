package ru.timuruktus.newsletters.Model.JSONFragments;


import android.util.Log;

public class UserAccount {

    public final static String TAG = "tag";

    String username;
    int rating;


    String email;

    public UserAccount(){}

    public UserAccount(String email){
        Log.d(TAG, "Email is UserAccount constructor: " + email);
        this.email = email;
        this.rating = 0;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
