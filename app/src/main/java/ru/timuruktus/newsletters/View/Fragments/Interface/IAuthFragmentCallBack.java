package ru.timuruktus.newsletters.View.Fragments.Interface;


public interface IAuthFragmentCallBack {

    public void changeFragment(boolean isItWasLogin);
    public void showRegError();
    public void showLoadingBarCallBack(boolean reverse);
}
