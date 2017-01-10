package ru.timuruktus.newsletters.Presenter;


public interface IAuthPresenter {

    public void onRegButClick(String email, String pass);
    public void onAuthSucceed(boolean IsItWasLogin);
    public void onAuthFailed();
}
