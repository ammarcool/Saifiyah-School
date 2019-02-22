package com.ammar.saifiyahschool.Gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.ammar.saifiyahschool.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumNames extends AppCompatActivity {

    DatabaseReference databaseReference,myDatabase;
    ArrayList<albumNamesData> albumNamesDataArrayList;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    SharedPreferences sharedPreferences;
    private ValueEventListener mDBListener;
    Toolbar toolbar;
    TextView noAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_names);

//        galleryGridView = (GridView) findViewById(R.id.galleryGridView);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Albums");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        noAlbum = findViewById(R.id.noAlbum);

        recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        albumNamesDataArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(getApplicationContext(), albumNamesDataArrayList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);


            databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

            mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        final DatabaseReference counter = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(postSnapshot.getRef().getKey());

                        final List<String> lst = new ArrayList<String>();
                        counter.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                lst.add(dataSnapshot.getKey());
                                Log.i("key===>", String.valueOf(dataSnapshot.getValue()));
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                            dataSnapshot.getRef().removeValue()
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        counter.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot ourdataSnapshot) {

                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString("counterNumber", String.valueOf(ourdataSnapshot.getChildrenCount()));
                                edit.apply();
                                Log.i("Counter--->", String.valueOf(ourdataSnapshot.child(lst.get(0)).child("url").getValue()));
                                albumNamesData upload = new albumNamesData(ourdataSnapshot.getRef().getKey(), sharedPreferences.getString("counterNumber", null), String.valueOf(ourdataSnapshot.child(lst.get(0)).child("url").getValue()));
                                albumNamesDataArrayList.add(upload);
                                myAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();

                            }
                        });


                    }
                    Log.i("sizeOnly-->", String.valueOf(albumNamesDataArrayList.size()));


                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (albumNamesDataArrayList == null) {
////
//            noAlbum.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//
//        } else {
//            noAlbum.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBListener);
    }
}
