package ru.timuruktus.newsletters.Model.FireBase;


import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import ru.timuruktus.newsletters.Presenter.PushPostPresenter;

public class UploadFiles {


    static Uri downloadUrl;
    public static Uri uploadPostImage(Bitmap localImg){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        localImg.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataBAOS = baos.toByteArray();

        /***************** UPLOADS THE PIC TO FIREBASE*****************/
        // Points to the root reference
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://newtag-5c9a6.appspot.com");
        StorageReference imagesRef = storageRef.child("postImages");

        UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();

            }
        });
        PushPostPresenter.onImageUploadListener();
        return downloadUrl;
    }
}
