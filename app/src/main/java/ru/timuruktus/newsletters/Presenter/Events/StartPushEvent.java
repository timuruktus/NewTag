package ru.timuruktus.newsletters.Presenter.Events;


public class StartPushEvent {

    public boolean start = false;
    public String returnedUrl;

    public StartPushEvent(boolean start, String returnedUrl){
        this.start = start;
        this.returnedUrl = returnedUrl;
    }
}
