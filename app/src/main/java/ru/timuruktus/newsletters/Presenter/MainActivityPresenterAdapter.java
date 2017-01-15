package ru.timuruktus.newsletters.Presenter;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * USED TO ASSOCIATE MainAcivityPresenter with other presenters.
 */
public class MainActivityPresenterAdapter {
    private MainActivityPresenter mainActivityPresenter;

    public MainActivityPresenterAdapter(MainActivityPresenter mainActivityPresenter){
        this.mainActivityPresenter = mainActivityPresenter;
    }

    public void changeEmailMenu(String email){
        mainActivityPresenter.changeEmailMenu(email);
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment fragment,
                                      boolean addToBachStack) {
        mainActivityPresenter.changeFragment(fragmentManager, fragment, addToBachStack);
    }

    public void hideLogout(boolean hide){
        mainActivityPresenter.hideLogout(hide);
    }
}
