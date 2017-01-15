package ru.timuruktus.newsletters.View.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.androidquery.AQuery;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ru.timuruktus.newsletters.Model.JSONFragments.Post;
import ru.timuruktus.newsletters.Presenter.WelcomePresenter;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Activities.IMainActivity;
import ru.timuruktus.newsletters.View.Activities.MainActivity;
import ru.timuruktus.newsletters.View.Fragments.Interface.IWelcomeFragment;
import ru.timuruktus.newsletters.View.Other.Adapters.UserPostsAdapter;

public class WelcomeFragment extends Fragment implements IWelcomeFragment {

    public static final String TAG = "tag";

    UserPostsAdapter userPostsAdapter;
    private ArrayList<Post> postArray = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WelcomePresenter wp = new WelcomePresenter(this);
        IMainActivity iMainActivity = (MainActivity) getActivity();
        iMainActivity.changeToolbarTitle(R.string.title_activity_main);
        View rootView =
                inflater.inflate(R.layout.welcome_fragment, container, false);

        userPostsAdapter = new UserPostsAdapter(rootView.getContext(), );

        ListView userPosts = (ListView) rootView.findViewById(R.id.userPosts);
        // TODO: Add posts to Adapter
        userPosts.setAdapter();
        userPosts.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }


}
