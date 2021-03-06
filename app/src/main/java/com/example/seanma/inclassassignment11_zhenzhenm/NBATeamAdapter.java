package com.example.seanma.inclassassignment11_zhenzhenm;

/**
 * Created by SeanMa on 5/1/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;

/**
 * Created by miket on 4/26/2017.
 */

public class NBATeamAdapter extends RecyclerView.Adapter<NBATeamAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<NBATeam> mDataset;
    private StorageReference mStorageRef;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NBATeamAdapter(ArrayList<NBATeam> dataset, Context context) {
        mDataset = dataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NBATeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // - holder.mTextView.setText(mDataset[position]);
        holder.mTeamName.setText(mDataset.get(position).getTeamName());
        holder.mTeamStadium.setText("Ranking: " + mDataset.get(position).getTeamStadium());
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference uploadRef = mStorageRef.child("images/" + mDataset.get(position).getFileName());

        try {
            final File localFile = File.createTempFile("images", "jpg");
            uploadRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            int i = holder.mTeamLogo.getWidth();
                            int j = holder.mTeamLogo.getHeight();
                            Picasso.with(mContext)
                                    .load(localFile)
                                    .resize(holder.mTeamLogo.getWidth(), holder.mTeamLogo.getHeight())
                                    .centerInside()
                                    .into(holder.mTeamLogo);
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
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("A Team", mDataset.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView mTeamName;
        public TextView mTeamStadium;
        public ImageView mTeamLogo;

        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTeamName = (TextView) v.findViewById(R.id.team_name);
            mTeamStadium = (TextView) v.findViewById(R.id.team_stadium);
            mTeamLogo = (ImageView) v.findViewById(R.id.team_logo);
        }
    }
}
