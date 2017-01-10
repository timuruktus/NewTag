package ru.timuruktus.newsletters.Presenter;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.androidquery.AQuery;

import ru.timuruktus.newsletters.Model.Auth.EmailAuth;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Fragments.Interface.IAuthFragmentCallBack;
import ru.timuruktus.newsletters.View.Fragments.WelcomeFragment;

public class AuthPresenter  {

    IAuthFragmentCallBack iAuthFragmentCallBack;

    public AuthPresenter(IAuthFragmentCallBack iAuthFragmentCallBack){
        this.iAuthFragmentCallBack = iAuthFragmentCallBack;

    }

    // !!!!!!UNDER THIS STATEMENT- SIGNALS FROM VIEW!!!!!!
    /**
     * Called when registration button
     * was clicked
     * @param email - email text
     * @param pass - password text
     */
    public void onRegButClick(String email, String pass){
        iAuthFragmentCallBack.showLoadingBarCallBack(true);
        EmailAuth auth = new EmailAuth(email,pass, this);
        auth.startAuth(EmailAuth.StartAction.REGISTER);
    }

    /**
     * Called when login button
     * was clicked
     * @param email - email text
     * @param pass - password text
     */
    public void onLoginButClick(String email, String pass){
        iAuthFragmentCallBack.showLoadingBarCallBack(true);
        EmailAuth auth = new EmailAuth(email,pass, this);
        auth.startAuth(EmailAuth.StartAction.LOGIN);
    }

    /**
     * Called when AuthFragment
     * should go away.
     * Goodnight, sweet prince ;)
     */
    public void onChangeFragment(FragmentManager fm) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, welcomeFragment);
    }


    // !!!!!!UNDER THIS STATEMENT- CALLBACKS FROM MODEL!!!!!!

    /**
     * If auth was failed (wrong email)/password, etc.)
     */
    public void onAuthFailed(){
        iAuthFragmentCallBack.showRegError();
    }

    /**
     * Called when auth was succeed.
     * @param isItWasLogin - - depends on what text will be shown in Toast
     *                     if "true" - you were signIn with login
     *                     if "false" - you were created your account
     */
    public void onAuthSucceed(boolean isItWasLogin){
        iAuthFragmentCallBack.showChangeFragment(isItWasLogin);

    }
}
