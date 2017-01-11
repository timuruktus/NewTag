package ru.timuruktus.newsletters.Presenter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Activities.IMainActivity;
import ru.timuruktus.newsletters.View.Activities.MainActivity;
import ru.timuruktus.newsletters.View.Fragments.AuthFragment;
import ru.timuruktus.newsletters.View.Fragments.WelcomeFragment;

public class MainActivityPresenter {

    private IMainActivity iMainActivity;
    public static final String TAG = "tag";

    public MainActivityPresenter(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            MainActivity.navigationView.getMenu().findItem(R.id.logout_menu).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.registration_menu).setVisible(true);
        }
        else{
            MainActivity.navigationView.getMenu().findItem(R.id.logout_menu).setVisible(true);
            MainActivity.navigationView.getMenu().findItem(R.id.registration_menu).setVisible(false);
        }
        initFirebaseAuthListener();
    }

    // !!!!!!UNDER THIS STATEMENT- SIGNALS FROM VIEW!!!!!!

    public void onLeftMenuButClick(MenuItem item, FragmentManager fragmentManager, DrawerLayout drawer){

        Fragment currentFragment = iMainActivity.getCurrentFragment();
        int id = item.getItemId();
        if(id != currentFragment.getId()) {
            if (id == R.id.news_menu) {
                changeFragment(fragmentManager, new WelcomeFragment(), true);
            } else if (id == R.id.tag_menu) {

            } else if (id == R.id.settings_menu) {

            } else if (id == R.id.registration_menu) {
                changeFragment(fragmentManager, new AuthFragment(), true);
            } else if (id == R.id.logout_menu){
                FirebaseAuth.getInstance().signOut();
                changeFragment(fragmentManager, new WelcomeFragment(), true);
                MainActivity.navigationView.getMenu().findItem(R.id.registration_menu).setVisible(true);
                MainActivity.navigationView.getMenu().findItem(R.id.logout_menu).setVisible(false);
                Log.d(TAG, "LOGOUT FROM MENU");

            }
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment,
                                      boolean addToBachStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(addToBachStack) {fragmentTransaction.addToBackStack(null);}
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void initFirebaseAuthListener(){
        Log.d(TAG, "AuthListener was initialised");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener]
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        // [END auth_state_listener]
        mAuth.addAuthStateListener(mAuthListener);
    }




}
