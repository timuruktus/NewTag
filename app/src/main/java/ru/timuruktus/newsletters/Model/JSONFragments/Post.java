package ru.timuruktus.newsletters.Model.JSONFragments;


public class Post {

    private String text;
    private String name;
    private String photoUrl;
    private String tags;
    private String id;

    public Post() {
    }

    public Post(String text, String name, String photoUrl, String tags, String id) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.tags = tags;
        this.id = id;
    }

    public Post(String text, String name, String id) {
        this.text = text;
        this.name = name;
        this.id = id;
    }

    public Post(String text, String name, String photoUrl, String id) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.id = id;
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
