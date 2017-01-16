package ru.timuruktus.newsletters.View.Fragments.Interface;


import java.util.ArrayList;

public interface IPushPostFragment {

    void turnOnLoading(boolean turn);
    void showError();
    void setCategoryArray(ArrayList<String> categories);

}
