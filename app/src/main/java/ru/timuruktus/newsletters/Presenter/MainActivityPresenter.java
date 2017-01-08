package ru.timuruktus.newsletters.Presenter;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.timuruktus.newsletters.Presenter.Events.MenuButClickEvent;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Fragments.AuthFragment;
import ru.timuruktus.newsletters.View.Fragments.WelcomeFragment;

public class MainActivityPresenter {

    public MainActivityPresenter(){
        EventBus.getDefault().register(this);
    }


    @Subscribe
    public void onMenuButClick(MenuButClickEvent event){
        FragmentTransaction fragmentTransaction = event.fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        int id = event.id;
        if (id == R.id.news_menu) {
            // RECENT ACTIVITY. NOTHING HAPPENS
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, welcomeFragment);
        } else if (id == R.id.tag_menu) {

        } else if (id == R.id.settings_menu) {

        } else if (id == R.id.registration_menu){
            AuthFragment authFragment = new AuthFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, authFragment);
        }
        fragmentTransaction.commit();
        event.drawer.closeDrawer(GravityCompat.START);
    }

}
