package com.example.seanma.inclassassignment11_zhenzhenm;

/**
 * Created by SeanMa on 5/1/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView infoTeamName = (TextView) findViewById(R.id.detail_team_name);
        TextView infoTeamRanking = (TextView) findViewById(R.id.detail_team_stadium);
        final ImageView infoTeamLogo = (ImageView) findViewById(R.id.default_pic);


        Intent intent = getIntent();
        NBATeam team = (NBATeam) intent.getSerializableExtra("A Team");

        infoTeamName.setText(team.getTeamName());
        infoTeamRanking.setText(team.getTeamStadium());

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference uploadRef = mStorageRef.child("images/" + team.getFileName());
        try {
            final File localFile = File.createTempFile("images", "jpg");
            uploadRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            Picasso.with(DetailActivity.this)
                                    .load(localFile)
                                    .resize(infoTeamLogo.getWidth(), infoTeamLogo.getHeight())
                                    .centerInside()
                                    .into(infoTeamLogo);
                            // ...
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}