package ru.timuruktus.newsletters.Model.FireBase.Listeners;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FireBaseListeners {

    public static final String TAG = "tag";

    public void initAuthListener(){
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
