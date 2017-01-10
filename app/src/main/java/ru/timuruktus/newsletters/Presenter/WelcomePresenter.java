package ru.timuruktus.newsletters.Presenter;


import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.newsletters.View.Fragments.Interface.IWelcomeFragment;

public class WelcomePresenter {

    IWelcomeFragment iWelcomeFragment;

    public WelcomePresenter(IWelcomeFragment iWelcomeFragment){
        this.iWelcomeFragment = iWelcomeFragment;
    }




}
