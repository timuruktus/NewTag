package ru.timuruktus.newsletters.Fragments;


public class Post {

    private String text;
    private String name;
    private String photoUrl;
    private String tags;

    public Post() {
    }

    public Post(String text, String name, String photoUrl, String tags) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
