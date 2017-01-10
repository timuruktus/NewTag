package ru.timuruktus.newsletters.Model.Auth;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.timuruktus.newsletters.Presenter.AuthPresenter;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Activities.MainActivity;

public class EmailAuth {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = "tag";
    private String email,pass;
    private AuthPresenter authPresenter;


    public EmailAuth(String email, String pass, AuthPresenter authPresenter){
        this.email = email;
        this.pass = pass;
        this.authPresenter = authPresenter;
    }

    public EmailAuth(){}

    /**
     * Starts authentication
     */
    public void startAuth(){
        initListenerAndDB();
        if(!signIn()){
            createAccount();
        }else{
            authPresenter.onAuthSucceed(true);
        }
    }

    /**
     * Init's login/logout listener and
     * Firebase dataBase
     */
    public void initListenerAndDB(){
        mAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
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

    /**
     * Used to create a new account
     */
    private void createAccount() {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            authPresenter.onAuthFailed();
            return;
        }
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.e(TAG,"mAuth.createUserWithEmailAndPassword.!task.isSuccessful()");
                            authPresenter.onAuthFailed();
                        }
                    }
                });
        // [END create_user_with_email]
            signIn();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }
        authPresenter.onAuthSucceed(false);
    }

    /**
     * Used to singIn
     * @return "false" if login was unsuccessful
     *          "true" if login was successful f.e.
     *          user hasn't been find in database
     */
    private boolean signIn() {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return false;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            return;
                        }
                    }
                });
        // [END sign_in_with_email]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            return true;
        } else {
            // No user is signed in
            return false;
        }
    }

    /**
     * Used to singOut of system
     */
    private void signOut() {
        mAuth.signOut();
    }

    /**
     * Checks if <field>email</field> and <field>password</field>
     * are correct
     * @return - false, if:
     * 1)password/email is empty
     * 2)email contains only numbers
     * 3)email doesn't contain "@" and "."
     * 4)password length < 6 letters
     */
    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) valid = false;
        if (TextUtils.isEmpty(pass)) valid = false;
        if (TextUtils.isDigitsOnly(email)) valid = false;
        if (!email.contains("@")) valid = false;
        if (!email.contains(".")) valid = false;
        if(pass.length()<6) valid = false;
        return valid;
    }

}
