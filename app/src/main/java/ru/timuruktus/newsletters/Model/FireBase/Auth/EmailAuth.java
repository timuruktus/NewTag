package ru.timuruktus.newsletters.Model.FireBase.Auth;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Random;

import ru.timuruktus.newsletters.Model.JSONFragments.Post;
import ru.timuruktus.newsletters.Model.JSONFragments.UserAccount;
import ru.timuruktus.newsletters.Presenter.AuthPresenter;

public class EmailAuth {
    private FirebaseAuth mAuth;
    public static final String TAG = "tag";
    private String email,pass;
    private AuthPresenter authPresenter;
    private DatabaseReference mDatabase;

    public enum StartAction{
        LOGIN, REGISTER
    }

    public EmailAuth(String email, String pass, AuthPresenter authPresenter){
        this.email = email;
        this.pass = pass;
        this.authPresenter = authPresenter;
    }

    /**
     * Starts authentication
     */
    public void startAuth(StartAction action){
        mAuth = FirebaseAuth.getInstance();
        if(action == StartAction.LOGIN) {
            signIn();
        }
        if(action == StartAction.REGISTER){
            createAccount();
        }

    }




    /**
     * Used to create a new account
     * By the end call AuthPresenter callback's
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
                            return;
                        }

                        signIn();
                        while (FirebaseAuth.getInstance().getCurrentUser() == null){
                            Log.d(TAG, "mAuth,onCompleteListener. Waiting for login...");
                        }
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }
                                    }
                                });
                        writeNewUser(email);
                        authPresenter.onAuthSucceed(false);
                    }
                });
        // [END create_user_with_email]
    }

    /**
     * Used to singIn
     * By the end call AuthPresenter callback's
     */
    private void signIn() {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            authPresenter.onAuthFailed();
            return;
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
                            authPresenter.onAuthFailed();
                            return;
                        }
                        authPresenter.onAuthSucceed(true);

                    }
                });
        // [END sign_in_with_email]
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
        if (pass.length()<6) valid = false;
        return valid;
    }

    public static void relog(String email, String pass){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });
    }


    private void writeNewUser(String email) {
        Log.d(TAG, "Email in writeNewUser(): " + email);
        UserAccount user = new UserAccount();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
    }


}
