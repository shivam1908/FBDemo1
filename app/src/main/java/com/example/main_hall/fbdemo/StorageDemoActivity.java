package com.example.main_hall.fbdemo;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

public class StorageDemoActivity extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference mainref;

    TextView tv1;
    ImageView imv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_demo);

        firebaseStorage = FirebaseStorage.getInstance();

        //Reference to Main Bucket
        mainref = firebaseStorage.getReference();

        tv1=findViewById(R.id.tv1);
        imv1=findViewById(R.id.imv1);


    }

    public void go(View v)
    {
        //Refer to one.jpg in default bucket
        StorageReference myfile = mainref.child("one.jpg");

        Task<StorageMetadata> mytask =  myfile.getMetadata();

        mytask.addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {

                tv1.setText("");

                tv1.append("Name "+storageMetadata.getName()+"\n");
                tv1.append("Size "+storageMetadata.getSizeBytes()+"\n");
                tv1.append("Type "+storageMetadata.getContentType()+"\n");
                tv1.append("Date "+ new Date(storageMetadata.getCreationTimeMillis())+"\n");
                tv1.append("Download URL "+storageMetadata.getDownloadUrl()+"\n");

                //Glide.with(getApplicationContext()).load(storageMetadata.getDownloadUrl()).into(imv1);

                Picasso.with(getApplicationContext()).load(storageMetadata.getDownloadUrl()).into(imv1);


                Log.d("MYMESSAGE","OnSuccess of MetaData");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tv1.setText("MetaData Fecthing Failed");

                Log.d("MYMESSAGE","OnFail of MetaData");
            }
        });



    }

    public void go2(View v)
    {
        String localfilename="two.mp4";
        File f=new File("/mnt/sdcard/"+localfilename);

        if(f.exists())
        {
            StorageReference myfile2 = mainref.child(localfilename);

            Uri uri= Uri.fromFile(f);

            UploadTask myuploadtask =  myfile2.putFile(uri);

            myuploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    tv1.setText("File Upload Complete...");
                    tv1.setTextColor(Color.GREEN);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    tv1.setText("File Upload Failed");
                    tv1.setTextColor(Color.RED);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                   int per = (int) (taskSnapshot.getBytesTransferred() *100
                                                                 / taskSnapshot.getTotalByteCount());

                   tv1.setText(per+" %");

                }
            });

        }
        else
        {
            Toast.makeText(this, "File Missing in /mnt/sdcard", Toast.LENGTH_SHORT).show();
        }
    }
}
