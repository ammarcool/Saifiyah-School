package com.ammar.saifiyahschool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;

import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class classTestAdapter extends RecyclerView.Adapter<classTestViewHolder> {

        ArrayList<classTestData> classTestListData ;
        Context classTestcontext;
        int series1Index;
        DecoView decoView;

        TextView testMarks, totalMarks;

        float percentage;
        float gettingMarks;
        int total;
        int score;
        BounceInterpolator bounceInterpolator;
        Interpolator interpolator = new BounceInterpolator();
        Typeface myCustomFont;


//    classTest classTest;

    public classTestAdapter(Context classTestContext, ArrayList<classTestData> classTestListData) {

        this.classTestcontext = classTestContext;
        this.classTestListData = classTestListData;
    }

    @NonNull
    @Override
    public classTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classtest_recyclerview,parent,false);
        return new classTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull classTestViewHolder holder, int position) {

        holder.testSubjects.setText(classTestListData.get(position).getTestSubjects());
        holder.month.setText(classTestListData.get(position).getMonthName());
        holder.day.setText(Integer.toString(classTestListData.get(position).getDayNumber()));
        holder.teacherNames.setText(classTestListData.get(position).getTeacherNames());
        holder.testMark.setText(Integer.toString(classTestListData.get(position).getTestMark()));
        holder.totalMark.setText(Integer.toString(classTestListData.get(position).getTotalMark()));

        //////----------------------------------Create Pie Chart-------------------------------//
        holder.decoView.configureAngles(360, 0);
        holder.decoView.setHorizGravity(DecoView.HorizGravity.GRAVITY_HORIZONTAL_FILL);
        holder.decoView.setVertGravity(DecoView.VertGravity.GRAVITY_VERTICAL_BOTTOM);

        //2. Create background track
        holder.decoView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(24f)
                .build()
        );

        //3. Create data Series track
        final SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(24f)
                .setInitialVisibility(false)
                .setCapRounded(true)

                .setShowPointWhenEmpty(true)
                .build();

                series1Index = holder.decoView.addSeries(seriesItem1);

        //4. Animation Data Series

        holder.decoView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(1000)
                .setDuration(2000)
                .build()
        );
        // 5. Add Listener for data Change
////        final String format = "%.0f%%";
////
////        seriesItem1.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
////            @Override
////            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
////
////                if (format.contains("%%")) {
////                    float percentFilled = ((currentPosition - seriesItem1.getMinValue()) /
////                            (seriesItem1.getMaxValue() - seriesItem1.getMinValue()));
////
////                    testMarks.setText(String.format(format, percentFilled * 100f));
////
////                } else {
////                    testMarks.setText(String.format(format, currentPosition));
////                }
////            }
////
////            @Override
////            public void onSeriesItemDisplayProgress(float percentComplete) {
////
////            }
////        });


        percentage = getScorePercent(classTestListData.get(position).getTestMark(),classTestListData.get(position).getTotalMark());


        if (percentage <= 15) {
            addAnimation(holder.decoView, series1Index, 15f, 2000, bounceInterpolator, RED, true);
        } else if (percentage <= 25) {
            addAnimation(holder.decoView, series1Index, 15f, 2000, bounceInterpolator, RED, true);
            addAnimation(holder.decoView, series1Index, 25f, 6000, bounceInterpolator, YELLOW, true);
        } else if (percentage <= 50) {
            addAnimation(holder.decoView, series1Index, 15f, 2000, bounceInterpolator, RED, true);
            addAnimation(holder.decoView, series1Index, 25f, 6000, bounceInterpolator, YELLOW, true);
            addAnimation(holder.decoView, series1Index, 50f, 10000, bounceInterpolator, GREEN, true);
        } else if (percentage <= 75) {
            addAnimation(holder.decoView, series1Index, 15f, 2000, bounceInterpolator, RED, true);
            addAnimation(holder.decoView, series1Index, 25f, 6000, bounceInterpolator, YELLOW, true);
            addAnimation(holder.decoView, series1Index, 50f, 10000, bounceInterpolator, GREEN, true);
            addAnimation(holder.decoView, series1Index, 75f, 14000, bounceInterpolator, CYAN, true);
        } else if (percentage <= 90) {
            addAnimation(holder.decoView, series1Index, 15f, 2000, bounceInterpolator, RED, true);
            addAnimation(holder.decoView, series1Index, 25f, 6000, bounceInterpolator, YELLOW, true);
            addAnimation(holder.decoView, series1Index, 50f, 10000, bounceInterpolator, GREEN, true);
            addAnimation(holder.decoView, series1Index, 75f, 14000, bounceInterpolator, CYAN, true);
            addAnimation(holder.decoView, series1Index, 95f, 18000, bounceInterpolator, MAGENTA, true);
        } else {
            addAnimation(holder.decoView, series1Index, 15f, 2000, bounceInterpolator, RED, true);
            addAnimation(holder.decoView, series1Index, 25f, 6000, bounceInterpolator, YELLOW, true);
            addAnimation(holder.decoView, series1Index, 50f, 10000, bounceInterpolator, GREEN, true);
            addAnimation(holder.decoView, series1Index, 75f, 14000, bounceInterpolator, CYAN, true);
            addAnimation(holder.decoView, series1Index, 95f, 18000, bounceInterpolator, MAGENTA, true);
            addAnimation(holder.decoView, series1Index, 100f, 22000, bounceInterpolator, DKGRAY, false);

        }

    }
    private void addAnimation(final DecoView arcView, int series, float moveTo, int delay, BounceInterpolator bounceInterpolator,
                              final int color, final boolean restart) {

//        DecoEvent.ExecuteEventListener listener = new DecoEvent.ExecuteEventListener() {
//            @Override
//            public void onEventStart(DecoEvent event) {
////                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), imageId));
////                showAvatar(true, imageView);
////                    mProgress = myformat;
//            }
//
//            @Override
//            public void onEventEnd(DecoEvent event) {
////                showAvatar(false, imageView);
////                if (restart) {
////                    setupEvents();
////                }
//            }
//        };

        arcView.addEvent(new DecoEvent.Builder(moveTo)
                .setIndex(series)
                .setDelay(delay)
                .setDuration(5000)
                .setInterpolator(interpolator)
                .setColor(color)
                .build());
    }
    public float getScorePercent(int score, int total) {
        this.score = score;
        this.total = total;
        gettingMarks = (score * 100 / total);
        return gettingMarks;
    }

//--------------------------- End Doughnut Pie chart-------------------------------------//

    @Override
    public int getItemCount() {
        return classTestListData.size();
    }
}


class classTestViewHolder extends RecyclerView.ViewHolder{

    TextView testMark;
    TextView totalMark;
    TextView testSubjects;
    TextView month;
    TextView day;
    TextView teacherNames;
    DecoView decoView;
    Typeface myCustomFont;


    public classTestViewHolder(@NonNull View itemView) {
        super(itemView);

        //font
        myCustomFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/book.ttf");
//        myCustomFont= getResources().getFont(R.font.book);


        testMark = itemView.findViewById(R.id.testMarks);
        testMark.setTypeface(myCustomFont);

        totalMark = itemView.findViewById(R.id.totalMarks);
        totalMark.setTypeface(myCustomFont);

        testSubjects = itemView.findViewById(R.id.testSubject);
        month = itemView.findViewById(R.id.leaveDay);
        day = itemView.findViewById(R.id.dateNumber);
        teacherNames = itemView.findViewById(R.id.teacherName);
        decoView = itemView.findViewById(R.id.dynamicArcView);

    }
}
