package ru.timuruktus.newsletters.Presenter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.timuruktus.newsletters.Model.Auth.EmailAuth;
import ru.timuruktus.newsletters.Presenter.Events.RegistrationEvent;
import ru.timuruktus.newsletters.View.Fragments.Interface.IAuthFragment;

public class AuthPresenter {

    IAuthFragment iAuthFragment;

    public AuthPresenter(IAuthFragment iAuthFragment){
        this.iAuthFragment = iAuthFragment;
        //EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onRegButClick(){


    }
}
