package ru.timuruktus.newsletters.Model.JSONFragments;


public class Post {

    private String text;
    private String newsImageUrl;
    private String authorImageUrl;
    private String tag;
    private String id;
    private String title;
    private String author; //Author- authId

    public Post() {
    }

    public Post(String text, String newsImageUrl, String authorImageUrl, String tag,
                 String id, String title, String author) {
        this.text = text;
        this.newsImageUrl = newsImageUrl;
        this.authorImageUrl = authorImageUrl;
        this.tag = tag;
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Post(String text, String authorImageUrl, String tag,
                String id, String title, String author) {
        this.text = text;
        this.authorImageUrl = authorImageUrl;
        this.tag = tag;
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostImageUrl() {
        return newsImageUrl;
    }

    public void setPostImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
