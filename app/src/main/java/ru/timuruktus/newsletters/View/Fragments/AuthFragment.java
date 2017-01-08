package ru.timuruktus.newsletters.View.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import ru.timuruktus.newsletters.Presenter.AuthPresenter;
import ru.timuruktus.newsletters.Presenter.Events.CallBackRegistrationEvent;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Fragments.Interface.IAuthFragment;

public class AuthFragment extends Fragment implements IAuthFragment {

    AuthPresenter authPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.auth_fragment, container, false);
        authPresenter = new AuthPresenter(this);
        return rootView;
    }


    @Override
    public void changeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, welcomeFragment);
    }
}
