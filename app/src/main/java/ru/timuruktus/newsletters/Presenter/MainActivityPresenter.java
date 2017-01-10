package ru.timuruktus.newsletters.Presenter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    }

    // !!!!!!UNDER THIS STATEMENT- SIGNALS FROM VIEW!!!!!!

    public void onLeftMenuButClick(MenuItem item, FragmentManager fragmentManager, DrawerLayout drawer){

        //FirebaseAuth.getInstance().getCurrentUser();
        Fragment currentFragment = iMainActivity.getCurrentFragment();
        int id = item.getItemId();
        if(id != currentFragment.getId()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (id == R.id.news_menu) {
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, welcomeFragment);
            } else if (id == R.id.tag_menu) {
                // DELETE THIS LATER!!!!!!!!!!!!!!!!!
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "LOGOUT FROM MENU");
            } else if (id == R.id.settings_menu) {

            } else if (id == R.id.registration_menu) {
                AuthFragment authFragment = new AuthFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, authFragment);
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public void loadMenuItems(Menu menu){

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            menu.findItem(R.id.registration_menu).setVisible(false);
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
        else if(FirebaseAuth.getInstance().getCurrentUser() == null){
            menu.findItem(R.id.registration_menu).setVisible(true);
            menu.findItem(R.id.logout_menu).setVisible(false);
        }
    }

}
