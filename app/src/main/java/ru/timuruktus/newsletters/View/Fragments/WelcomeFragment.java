package ru.timuruktus.newsletters.View.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.newsletters.Presenter.WelcomePresenter;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Fragments.Interface.IWelcomeFragment;

public class WelcomeFragment extends Fragment implements IWelcomeFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WelcomePresenter wp = new WelcomePresenter(this);
        View rootView =
                inflater.inflate(R.layout.welcome_fragment, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }


}
