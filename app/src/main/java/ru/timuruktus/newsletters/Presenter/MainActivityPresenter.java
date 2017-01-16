package ru.timuruktus.newsletters.Presenter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.timuruktus.newsletters.Model.FireBase.Listeners.FireBaseListeners;
import ru.timuruktus.newsletters.Model.JSONFragments.Post;
import ru.timuruktus.newsletters.Model.JSONFragments.UserAccount;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Activities.IMainActivity;
import ru.timuruktus.newsletters.View.Activities.MainActivity;
import ru.timuruktus.newsletters.View.Fragments.AuthFragment;
import ru.timuruktus.newsletters.View.Fragments.PushPostFragment;
import ru.timuruktus.newsletters.View.Fragments.WelcomeFragment;

public class MainActivityPresenter {

    private IMainActivity iMainActivity;
    public static final String TAG = "tag";
    public static MainActivityPresenterAdapter mainActivityPresenterAdapter;
    private FireBaseListeners fireBaseListeners;

    public MainActivityPresenter(IMainActivity iMainActivity){
        this.mainActivityPresenterAdapter = new MainActivityPresenterAdapter(this);
        this.iMainActivity = iMainActivity;
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            hideLogout(true);
        }
        else{
            hideLogout(false);
        }
        initFirebaseAuthListener();
    }

    // !!!!!!UNDER THIS STATEMENT- SIGNALS FROM VIEW!!!!!!

    public void onLeftMenuButClick(MenuItem item, FragmentManager fragmentManager, DrawerLayout drawer){
        if(!hasConnection(drawer.getContext())){
            Toast.makeText(drawer.getContext(), R.string.disabled_network, Toast.LENGTH_LONG).show();
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        Fragment currentFragment = iMainActivity.getCurrentFragment();
        int id = item.getItemId();
        if(id != currentFragment.getId()) {
            if (id == R.id.news_menu) {
                changeFragment(fragmentManager, new WelcomeFragment(), true);
            } else if (id == R.id.post_menu) {
                changeFragment(fragmentManager, new PushPostFragment(), true);
            } else if (id == R.id.settings_menu) {

            } else if (id == R.id.registration_menu) {
                changeFragment(fragmentManager, new AuthFragment(), true);
            } else if (id == R.id.logout_menu){
                FirebaseAuth.getInstance().signOut();
                changeFragment(fragmentManager, new WelcomeFragment(), true);
                hideLogout(true);
                Log.d(TAG, "LOGOUT FROM MENU");

            }
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment fragment,
                                      boolean addToBachStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(addToBachStack) {fragmentTransaction.addToBackStack(null);}
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void initFirebaseAuthListener(){
        Log.d(TAG, "AuthListener was initialised");
        fireBaseListeners = new FireBaseListeners();
        fireBaseListeners.initAuthListener();
    }


    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public void hideLogout(boolean hide){
        if(hide){
            MainActivity.navigationView.getMenu().findItem(R.id.logout_menu).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.registration_menu).setVisible(true);
        }
        else{
            MainActivity.navigationView.getMenu().findItem(R.id.logout_menu).setVisible(true);
            MainActivity.navigationView.getMenu().findItem(R.id.registration_menu).setVisible(false);
        }
    }




}
