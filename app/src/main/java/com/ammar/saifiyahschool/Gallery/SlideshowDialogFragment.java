package com.ammar.saifiyahschool.Gallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;


import com.ammar.saifiyahschool.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Upload> uploadArrayList = new ArrayList<>();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    MediaController mediaController;
    LinearLayout videoLinearLayout;
    int newPosition = 0;

    SimpleExoPlayerView exoPlayerView = null;
    SimpleExoPlayer exoPlayer = null;
    String videoURL = "https://firebasestorage.googleapis.com/v0/b/albumgallery-db23c.appspot.com/o/uploads%2FFirst%20Video.mp4?alt=media&token=9d7a22bc-9b75-47c2-9762-f47b6565ba2c";

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        videoLinearLayout = (LinearLayout)v.findViewById(R.id.videoLinearLayout);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        uploadArrayList = (ArrayList<Upload>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + uploadArrayList.size());


        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(selectedPosition);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        this.getDialog().setCanceledOnTouchOutside(false);
//        getDialog().dismiss();
        setCurrentItem(selectedPosition);

               getDialog().setCanceledOnTouchOutside(true);

        return v;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null){
            exoPlayer.release();
        }else {
//            exoPlayer.setPlayWhenReady(true);
            Log.i(TAG, "onStop: ");
        }
        Log.i("this_onStop", "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();

        if (exoPlayer != null){
            exoPlayerView.setPlayer(null);
            exoPlayer.release();
        }else {
//            exoPlayer.setPlayWhenReady(true);
            Log.i(TAG, "onPause: ");
        }
        dismissAllowingStateLoss();
        Log.i("Pause_Only", "onPause: "+"thank you onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (exoPlayer != null){
            exoPlayerView.setPlayer(null);
            exoPlayer.release();
        }else {
//            exoPlayer.setPlayWhenReady(true);
            Log.i("NULL==>", "onDestroy: ");
        }
        Log.i("onDestroy", "Bhai bhai: ");
    }


    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }


    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

                displayMetaInfo(position);

            Log.i(TAG, "onPageSelected: "+position);

        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            Log.i(TAG, "onPageScrolled: "+position);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("OurPosition",String.valueOf(position));
            edit.apply();
//            Upload upload = uploadArrayList.get(position);
//            SharedPreferences.Editor edit = sharedPreferences.edit();
//            edit.putString("LINK", upload.getUrl());
//            edit.apply();
//            Toast.makeText(getContext(), String.valueOf(upload.getName()+" "+arg1+" "+arg2), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            newPosition = arg0;
        }


    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + uploadArrayList.size());

        Upload upload = uploadArrayList.get(position);


        String string = upload.getUrl();



            if (string.contains(".mp4")  ){

                Log.i("DATA-->",string+" "+position);
                if (exoPlayer == null || exoPlayer != null){
//                    exoPlayerView.setPlayer(null);
//                    exoPlayer.release();
                if (exoPlayer != null){
                    exoPlayer.release();
                    videoLinearLayout.setVisibility(View.GONE);

                    Log.i(TAG, "displayMetaInfo: "+ newPosition);

                    try {

                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

                        Uri videoURI = Uri.parse(upload.getUrl());

                        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                        MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                        exoPlayerView.setPlayer(exoPlayer);
                        exoPlayer.prepare(mediaSource);
                        exoPlayer.setPlayWhenReady(false);

                    } catch (Exception e) {
                        Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                    }
                }

                videoLinearLayout.setVisibility(View.GONE);

                Log.i(TAG, "displayMetaInfo: "+ newPosition);

                    try {

                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

                        Uri videoURI = Uri.parse(upload.getUrl());

                        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                        MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                        exoPlayerView.setPlayer(exoPlayer);
                        exoPlayer.prepare(mediaSource);
                        exoPlayer.setPlayWhenReady(false);

                    } catch (Exception e) {
                        Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                    }

                }else {
//            exoPlayer.setPlayWhenReady(true);
                    Log.i(TAG, "onPause: ");
                }

            }else if (exoPlayer != null){
                exoPlayer.stop();
                videoLinearLayout.setVisibility(View.VISIBLE);
                lblTitle.setText(upload.getName());
                lblDate.setText(" ");

            }else {
                videoLinearLayout.setVisibility(View.VISIBLE);
                lblTitle.setText(upload.getName());
                lblDate.setText(" ");
            }

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setRetainInstance(true);
    }

    //  adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        Context context;
        View view;
        ProgressDialog progressDialog;
        public MyViewPagerAdapter() {
        }


        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Upload upload = uploadArrayList.get(position);
            String string = upload.getUrl();
            Uri videoURI = Uri.parse(string);

//            Log.i(TAG, "instantiateItem: "+sharedPreferences.getString("position",null));

            if (string.contains(".mp4") ){
                view = layoutInflater.inflate(R.layout.video_player, container, false);
//                progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.show();

//                        progressDialog.dismiss();

                exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player_view);

                if (position == selectedPosition) {
                    try {

                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);


                        Log.i("VideoURI-->", videoURI.toString());

                        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(string), dataSourceFactory, extractorsFactory, null, null);

                        exoPlayerView.setPlayer(exoPlayer);
                        exoPlayer.prepare(mediaSource);
                        exoPlayer.setPlayWhenReady(false);

                    } catch (Exception e) {
                        Log.e("MainActivity", " exoplayer error " + e.toString());
                    }

                }
//                else{
//
//
//                    videoLinearLayout.setVisibility(View.GONE);
//
//                    Log.i(TAG, "displayMetaInfo: "+ newPosition);
//
////                    try {
////
////                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
////                        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
////                        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
////
////                        Uri OurvideoURI = Uri.parse(upload.getUrl());
////
////                        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
////                        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
////                        MediaSource mediaSource = new ExtractorMediaSource(OurvideoURI, dataSourceFactory, extractorsFactory, null, null);
////
////                        exoPlayerView.setPlayer(exoPlayer);
////                        exoPlayer.prepare(mediaSource);
////                        exoPlayer.setPlayWhenReady(false);
////
////                    } catch (Exception e) {
////                        Log.e("MainAcvtivity", " exoplayer error " + e.toString());
////                    }
//
//
//                }
            }else {

                view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
                ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

//                imageViewPreview.setVisibility(View.VISIBLE);
//                videoView.setVisibility(View.GONE);
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                Log.d("LinkImage--->", upload.getUrl());
                Glide.with(getActivity()).load(string)
                        .transition(withCrossFade())
                        .thumbnail(0.5f)
                        .apply(requestOptions)
                        .into(imageViewPreview);
            }
            container.addView(view);

            return view;
        }


        @Override
        public int getCount() {
            return uploadArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

            Log.i(TAG, "destroyItem: "+ "this is destroyItem bhai");
        }

    }
}
