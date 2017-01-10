package ru.timuruktus.newsletters.Presenter;


import com.androidquery.AQuery;

import ru.timuruktus.newsletters.Model.Auth.EmailAuth;
import ru.timuruktus.newsletters.View.Fragments.Interface.IAuthFragmentCallBack;

public class AuthPresenter implements IAuthPresenter {

    IAuthFragmentCallBack iAuthFragmentCallBack;

    public AuthPresenter(IAuthFragmentCallBack iAuthFragmentCallBack){
        this.iAuthFragmentCallBack = iAuthFragmentCallBack;


    }

    /**
     * Called when registration button
     * was clicked
     * @param email - email text
     * @param pass - password text
     */
    public void onRegButClick(String email, String pass){
        iAuthFragmentCallBack.showLoadingBarCallBack(false);
        EmailAuth auth = new EmailAuth(email,pass, this);
        auth.startAuth();
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
        iAuthFragmentCallBack.changeFragment(isItWasLogin);
    }
}
