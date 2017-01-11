package ru.timuruktus.newsletters.Model.JSONFragments;



public class UserAccount {

    String username;
    int rating;
    String email;
    String id;

    public UserAccount(){}

    public UserAccount(String email, String id){
        // TODO CHECK WHAT'S WRONG- EMAIL COULD BE EMPTY
        this.email = email;
        this.id = id;
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





}
