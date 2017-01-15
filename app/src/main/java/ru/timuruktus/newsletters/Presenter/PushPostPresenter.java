package ru.timuruktus.newsletters.Presenter;


import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.timuruktus.newsletters.View.Fragments.Interface.IPushPostFragment;

public class PushPostPresenter {

    IPushPostFragment iPushPostFragment;
    private DatabaseReference mDatabase;
    public static final String TAG = "tag";

    public PushPostPresenter(IPushPostFragment pushPostFragment){
        this.iPushPostFragment = pushPostFragment;
    }

    public ArrayList<String> getCategories(){
        final ArrayList<String> categories = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = mDatabase.child("Category");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    categories.add(postSnapshot.toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        return categories;
    }

    public void onPushButtonClick(Bitmap localImg){
        // TODO: REALISE THAT THING
        iPushPostFragment.turnOnLoading(true);

    }
}
