package com.ammar.saifiyahschool.Gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ammar.saifiyahschool.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<albumNamesData> uploads;

    public MyAdapter(Context context, List<albumNamesData> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        albumNamesData upload = uploads.get(position);

        holder.textViewName.setText(upload.getAlbumName());
        holder.albumItemCount.setText(upload.getAlbumItemCount());

        Glide.with(context)
                .load(upload.getAlbumImageURL())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName,albumItemCount;
        public ImageView imageView;
        private DatabaseReference mDatabase;
        private FirebaseStorage mStorage;
        StorageReference storageReference;

        Context context;

        public ViewHolder(final View itemView) {
            super(itemView);

            context = itemView.getContext();
            mStorage = FirebaseStorage.getInstance();
            textViewName = (TextView) itemView.findViewById(R.id.gallery_title);
            imageView = (ImageView) itemView.findViewById(R.id.galleryImage);
            albumItemCount = itemView.findViewById(R.id.gallery_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(context,ViewGallery.class);
                    intent.putExtra("Album",uploads.get(getAdapterPosition()).getAlbumName());
                    context.startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Leave Approval")
                            .setMessage("Do you really want to Remove this Album ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface dialog, int whichButton) {

                                mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(uploads.get(getAdapterPosition()).getAlbumName());

                                //adding an event listener to fetch values
                                mDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {

                                        //iterating through all the values in database
                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                                            //get album names
                                            String albumNames = postSnapshot.getRef().getKey();
                                            Log.i("Album_Names--->", String.valueOf(snapshot.child(albumNames).child("name").getValue()));
                                            storageReference = mStorage.getReference(Constants.STORAGE_PATH_UPLOADS+snapshot.child(albumNames).child("name").getValue());
                                            storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(context,"File deleted",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                mDatabase.removeValue();

                        }})
                        .setNegativeButton(android.R.string.no, null).show();

                    return false;
                }
            });


        }

    }
}
