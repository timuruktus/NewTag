package ru.timuruktus.newsletters.View.Fragments.Interface;


public interface IAuthFragmentCallBack {

    void showChangeFragment(boolean isItWasLogin);
    void showRegError();
    void showLoadingBarCallBack(boolean hide);
}
