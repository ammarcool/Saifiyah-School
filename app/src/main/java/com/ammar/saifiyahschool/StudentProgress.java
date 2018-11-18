package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private String type,id,ip,URL,class_id;
    private TextView tv;
    private SharedPreferences sharedPreferences;

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

        runProgressBar();

    }

    private void runProgressBar() {
        prb.setMax(100);
        prb.setProgress(0);

        prb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_success_progressbar));
        progressBarComplain.setProgressDrawable(getResources().getDrawable(R.drawable.custom_complain_progressbar));

//        //this is for progress bar
//        for (int i =0;i<studentProgressDataArrayList.size();i++){
//            Log.i("my Arraylist "+i, String.valueOf(Integer.valueOf(studentProgressDataArrayList.get(i).getCard())));
//
//            if(studentProgressDataArrayList.get(i).getCard()==R.drawable.greencard){
//                Log.i("Green Card","this is a Green Card");
//                GreenProgressNumber+=5;
//            }
//            else{
//                Log.i("Red Card","Red Card hai bhai");
//                redProgressNumber+=5;
//            }
//
//        }

//        final Thread thread = new Thread() {
//            @Override
//            public void run() {
//                for (int i = 0; i <= 100; i++) {
//                    try {
//                        final int finalI = i;
//                        final int success= GreenProgressNumber;
//                        final int complain=redProgressNumber;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(success >= finalI){
//                                    prb.setProgress(finalI);
//                                    successPercentage.setText(Integer.toString(finalI)+"%");
//                                    Log.i("Success value",Integer.toString(finalI));
//
//                                }
//                                if (complain >=finalI){
//                                    progressBarComplain.setProgress(finalI);
//                                    complainPercentage.setText(Integer.toString(finalI)+"%");
//                                    Log.i("Complain value",Integer.toString(finalI));
//                                }
//
//                            }
//                        });
//                        sleep(60);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        thread.start();
    }

    private void addStudentProgressData() {

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        class_id = sharedPreferences.getString("student_class_id",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        if(!TextUtils.isEmpty(class_id) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(ip))
        {
            URL = "http://" + ip + "/school_cms/Progresses/progress.json";

            Map<String, String> params = new HashMap();
            params.put("student_id", id);
            params.put("student_class_id", class_id);
            JSONObject parameters = new JSONObject(params);

            RequestQueue requestQueue = Volley.newRequestQueue(StudentProgress.this);

            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    parameters,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray jsonArray = null;
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String success = jsonObject.getString("success");
                                if (success.equals("true")) {
                                    jsonArray = jsonObject.getJSONArray("response");

                                    StudentProgressData studentProgressData ;
                                    int redCard=R.drawable.redcard;
                                    int greenCard=R.drawable.greencard;
                                    int card = 0;

                                    for(int i = 0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject res = (JSONObject) jsonArray.get(i);

                                        if(res.getString("is_reward").equals("true")){
                                            card = greenCard;
                                            GreenProgressNumber+=5;
                                        }
                                        else {
                                            card = redCard;
                                            redProgressNumber+=5;
                                        }
                                        studentProgressData= new StudentProgressData(card,res.getString("date"),res.getString("title"));
                                        studentProgressDataArrayList.add(studentProgressData);

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
                                    studentprogressAdapter.notifyDataSetChanged();


                                } else {
                                    String msg = jsonObject.getString("message");
                                    tv.setText(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                            } else if (error instanceof ServerError) {
                            } else if (error instanceof AuthFailureError) {
                            } else if (error instanceof ParseError) {
                            } else if (error instanceof NoConnectionError) {
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(StudentProgress.this, "Oops. Timeout error!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
            objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(objectRequest);

        }
        else
            tv.setText("Uh-oh Something Went Wrong");

    }

}