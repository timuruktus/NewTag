package ru.timuruktus.newsletters.Presenter.Events;


import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

public class MenuButClickEvent {
    public int id;
    public FragmentManager fragmentManager;
    public DrawerLayout drawer;

    public MenuButClickEvent(int id, FragmentManager fragmentManager, DrawerLayout drawer){
        this.id = id;
        this.fragmentManager = fragmentManager;
        this.drawer = drawer;
    }
}
