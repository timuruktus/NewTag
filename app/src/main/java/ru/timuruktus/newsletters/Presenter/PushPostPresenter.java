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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import ru.timuruktus.newsletters.Model.FireBase.UploadFiles;
import ru.timuruktus.newsletters.Model.JSONFragments.Post;
import ru.timuruktus.newsletters.Model.JSONFragments.UserAccount;
import ru.timuruktus.newsletters.Presenter.Events.StartPushEvent;
import ru.timuruktus.newsletters.View.Fragments.Interface.IPushPostFragment;

public class PushPostPresenter {

    IPushPostFragment iPushPostFragment;
    private DatabaseReference mDatabase;
    public static final String TAG = "tag";
    private String username,text,title,tag;
    Bitmap localImg;
    private static boolean waiting = true;
    private ArrayList<String> categories;
    private static String urlToImage;


    public PushPostPresenter(IPushPostFragment pushPostFragment){
        this.iPushPostFragment = pushPostFragment;
        EventBus.getDefault().register(this);
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

    /**
     * **DOWNLOAD IMAGE TO SERVER FIRST**
     * @param localImg
     * @param text
     * @param title
     * @param tag
     */
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
        UploadFiles.uploadPostImage(localImg);
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

    public void getAuthorLogin() throws NullPointerException{
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                if(userAccount.getUsername() == null){
                    PushPostPresenter.this.username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                }else{
                    PushPostPresenter.this.username = userAccount.getUsername();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(postListener);
    }

    public void loadPostToDB(){
        try {
            Post post;
            getAuthorLogin();
            if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() == null){
                String defaultIMGUrl = "http://img.freeflagicons.com/thumb/square_icon/russia/russia_640.png";
                post = new Post(text, urlToImage, defaultIMGUrl, tag, title, username);
            }else {
                post = new Post(text, urlToImage,
                        FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), tag, title,
                        username);
            }
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
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    child("Posts").
                    setValue(post);
            EventBus.getDefault().unregister(this);
        }catch (NullPointerException ex){
            ex.printStackTrace();
            iPushPostFragment.showError();
        }

    }

    @Subscribe
    public void onImageUploadListener(StartPushEvent event){
        if(!event.start) {
            iPushPostFragment.showError();
        }else{
            this.urlToImage = event.returnedUrl;
            loadPostToDB();
        }
    }
}
