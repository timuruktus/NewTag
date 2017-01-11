package ru.timuruktus.newsletters.View.Fragments.Interface;


public interface IAuthFragment {

    void showChangeFragment(boolean isItWasLogin);
    void showRegError();
    void showLoadingBarCallBack(boolean hide);
}
