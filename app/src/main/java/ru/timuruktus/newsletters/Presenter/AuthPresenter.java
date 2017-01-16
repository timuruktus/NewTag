package ru.timuruktus.newsletters.Presenter;


import android.app.FragmentManager;

import ru.timuruktus.newsletters.Model.FireBase.Auth.EmailAuth;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Activities.MainActivity;
import ru.timuruktus.newsletters.View.Fragments.Interface.IAuthFragment;
import ru.timuruktus.newsletters.View.Fragments.WelcomeFragment;

public class AuthPresenter  {

    private IAuthFragment iAuthFragment;
    private String email;

    public AuthPresenter(IAuthFragment iAuthFragment){
        this.iAuthFragment = iAuthFragment;

    }

    // !!!!!!UNDER THIS STATEMENT- SIGNALS FROM VIEW!!!!!!
    /**
     * Called when registration button
     * was clicked
     * @param email - email text
     * @param pass - password text
     */
    public void onRegButClick(String email, String pass){
        this.email = email;
        iAuthFragment.showLoadingBar(true);
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
        this.email = email;
        iAuthFragment.showLoadingBar(true);
        EmailAuth auth = new EmailAuth(email,pass, this);
        auth.startAuth(EmailAuth.StartAction.LOGIN);
    }

    /**
     * Called when AuthFragment
     * should go away.
     * <joke>Goodnight, sweet prince ;)</joke>
     */
    public void onChangeFragment(FragmentManager fm) {
        MainActivityPresenter.mainActivityPresenterAdapter.changeFragment(fm, new WelcomeFragment(), false);
        MainActivityPresenter.mainActivityPresenterAdapter.hideLogout(false);
    }


    // !!!!!!UNDER THIS STATEMENT- CALLBACKS FROM MODEL!!!!!!

    /**
     * If auth was failed (wrong email)/password, etc.)
     */
    public void onAuthFailed(){
        iAuthFragment.showRegError();
    }

    /**
     * Called when auth was succeed.
     * @param isItWasLogin - - depends on what text will be shown in Toast
     *                     if "true" - you were signIn with login
     *                     if "false" - you were created your account
     */
    public void onAuthSucceed(boolean isItWasLogin){
        iAuthFragment.showChangeFragment(isItWasLogin);
    }
}
