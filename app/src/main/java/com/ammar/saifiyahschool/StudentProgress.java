package com.ammar.saifiyahschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;

public class StudentProgress extends AppCompatActivity {

    ProgressBar prb, progressBarComplain;
    TextView successPercentage, complainPercentage;
    int redProgressNumber=0;
    int GreenProgressNumber=0;

    private ArrayList<StudentProgressData> studentProgressDataArrayList = new ArrayList<>();
    private RecyclerView studentProgressRecyclerView;
    private StudentprogressAdapter studentprogressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_progress);

        //Student Progress Recyclerview
        studentProgressRecyclerView = findViewById(R.id.studentProgress_recyclerview);

        studentprogressAdapter = new StudentprogressAdapter(studentProgressDataArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        studentProgressRecyclerView.setLayoutManager(mLayoutManager);
        studentProgressRecyclerView.setItemAnimator(new DefaultItemAnimator());
        studentProgressRecyclerView.setAdapter(studentprogressAdapter);

        addStudentProgressData();

        prb = (ProgressBar) findViewById(R.id.progressBarSuccess);
        progressBarComplain = (ProgressBar) findViewById(R.id.progressBarComplain);
        successPercentage = (TextView) findViewById(R.id.successPercentage);
        complainPercentage = (TextView) findViewById(R.id.complainPercentage);

        prb.setMax(100);
        prb.setProgress(0);

        prb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_success_progressbar));
        progressBarComplain.setProgressDrawable(getResources().getDrawable(R.drawable.custom_complain_progressbar));

        //this is for progress bar
        for (int i =0;i<studentProgressDataArrayList.size();i++){
            Log.i("my Arraylist "+i, String.valueOf(Integer.valueOf(studentProgressDataArrayList.get(i).getCard())));

            if(studentProgressDataArrayList.get(i).getCard()==R.drawable.greencard){
                Log.i("Green Card","this is a Green Card");
                GreenProgressNumber+=5;
            }
            else{
                Log.i("Red Card","Red Card hai bhai");
                redProgressNumber+=5;
            }

        }

        final Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    try {
                        final int finalI = i;
                        final int success= GreenProgressNumber;
                        final int complain=redProgressNumber;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(success >= finalI){
                                    prb.setProgress(finalI);
                                    successPercentage.setText(Integer.toString(finalI)+"%");
                                    Log.i("Success value",Integer.toString(finalI));

                                }
                                if (complain >=finalI){
                                    progressBarComplain.setProgress(finalI);
                                    complainPercentage.setText(Integer.toString(finalI)+"%");
                                    Log.i("Complain value",Integer.toString(finalI));
                                }

                            }
                        });
                        sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();


    }

    private void addStudentProgressData() {

        int redCard=R.drawable.redcard;
        int greenCard=R.drawable.greencard;

        StudentProgressData studentProgressData ;


        studentProgressData= new StudentProgressData(greenCard,"10-dec-2018","He's really good student and keep in Decipline");
        studentProgressDataArrayList.add(studentProgressData);

        studentProgressData = new StudentProgressData(redCard, "10-dec-2018", "He's really good student and keep in Decipline");
        studentProgressDataArrayList.add(studentProgressData);

        studentProgressData = new StudentProgressData(greenCard, "10-dec-2018", "He's really good student and keep in Decipline");
        studentProgressDataArrayList.add(studentProgressData);

        studentProgressData = new StudentProgressData(redCard, "10-dec-2018", "He's really good student and keep in Decipline");
        studentProgressDataArrayList.add(studentProgressData);

        studentProgressData = new StudentProgressData(greenCard, "10-dec-2018", "He's really good student and keep in Decipline");
        studentProgressDataArrayList.add(studentProgressData);

        studentProgressData = new StudentProgressData(greenCard, "10-dec-2018", "He's really good student and keep in Decipline");
        studentProgressDataArrayList.add(studentProgressData);
        studentprogressAdapter.notifyDataSetChanged();

    }

}