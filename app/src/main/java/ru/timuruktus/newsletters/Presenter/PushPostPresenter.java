package ru.timuruktus.newsletters.Presenter;


import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.timuruktus.newsletters.Model.FireBase.UploadFiles;
import ru.timuruktus.newsletters.Model.JSONFragments.Post;
import ru.timuruktus.newsletters.Model.JSONFragments.UserAccount;
import ru.timuruktus.newsletters.View.Fragments.Interface.IPushPostFragment;

public class PushPostPresenter {

    IPushPostFragment iPushPostFragment;
    private DatabaseReference mDatabase;
    public static final String TAG = "tag";
    private String username,text,title,tag, urlToImage;
    Bitmap localImg;
    private static boolean waiting = true;
    private ArrayList<String> categories;


    public PushPostPresenter(IPushPostFragment pushPostFragment){
        this.iPushPostFragment = pushPostFragment;
    }

    public void getCategories(){
        categories = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    categories.add(postSnapshot.getValue().toString());
                    System.out.println(postSnapshot.getValue().toString());
                }
                iPushPostFragment.setCategoryArray(categories);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void onPushButtonClick(Bitmap localImg, String text, String title,
                                  String tag){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            iPushPostFragment.showError();
            return;
        }
        iPushPostFragment.turnOnLoading(true);
        this.text = text;
        this.tag = tag;
        this.title = title;
        this.urlToImage = UploadFiles.uploadPostImage(localImg).toString();
        while(waiting){
            Log.d(TAG, "Waiting to load image to server");
        }
        loadPostToDB();
    }

    public void onPushButtonClick(String urlToImage, String text, String title,
                                  String tag){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            iPushPostFragment.showError();
            return;
        }
        iPushPostFragment.turnOnLoading(true);
        this.urlToImage = urlToImage;
        this.text = text;
        this.title = title;
        this.tag = tag;
        loadPostToDB();
    }

    public String getAuthorLogin() throws NullPointerException{
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                if(userAccount.getUsername() == null){
                    username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                }else{
                    username = userAccount.getUsername();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        // TODO Айди заменить на почту. Все будет базироваться не на id,а на почте
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addListenerForSingleValueEvent(postListener);
        return username;
    }

    public void loadPostToDB(){
        try {
            Post post = new Post(text, urlToImage,
                    FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), tag, title,
                    getAuthorLogin());
            /**
             *      -UserPosts
             *          -postId
             *              -post
             *      -Users
             *          -currentUserEmail
             *              -Posts
             *                  -currentPost
             */
            String postID = mDatabase.child("UsersPosts").push().getKey();
            mDatabase.child("UsersPosts").child(postID).setValue(post);
            mDatabase.child("Users").
                    child(FirebaseAuth.getInstance().getCurrentUser().getEmail()).
                    child("Posts").
                    setValue(post);
        }catch (NullPointerException ex){
            ex.printStackTrace();
            iPushPostFragment.showError();
        }
    }


    public static void onImageUploadListener(){
        waiting = false;
    }
}
