package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hookedonplay.decoviewlib.DecoView;

import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class classTest extends AppCompatActivity {

    private String type,id,ip,URL,class_id;
    private TextView tv;
    private ArrayList<classTestData> classTestDataList = new ArrayList<>();
    private RecyclerView classTestRecyclerView;
    private classTestAdapter classTestAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test);

        classTestRecyclerView = findViewById(R.id.classTestRecycler);

        classTestAdapter = new classTestAdapter(this, classTestDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        classTestRecyclerView.setLayoutManager(mLayoutManager);
        classTestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        classTestRecyclerView.setAdapter(classTestAdapter);

        addClassTestMarks();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar Title");
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Class Test");

//        //font
//        myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/book.ttf");
////        myCustomFont= getResources().getFont(R.font.book);
//        testMarks.setTypeface(myCustomFont);
//        totalMarks.setTypeface(myCustomFont);


    }

    private void addClassTestMarks() {

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        class_id = sharedPreferences.getString("student_class_section_id",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        if(!TextUtils.isEmpty(class_id) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(ip))
        {
            URL = "http://" + ip + "/school_cms/ClassTestRows/classTest.json";

            Map<String, String> params = new HashMap();
            params.put("student_id", id);
            params.put("student_class_section_id", class_id);
            JSONObject parameters = new JSONObject(params);

            RequestQueue requestQueue = Volley.newRequestQueue(classTest.this);

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

                                    for(int i = 0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject res = (JSONObject) jsonArray.get(i);

                                        classTestData classTestData = new classTestData(res.getString("subject"), res.getString("month"), Integer.parseInt(res.getString("day")), res.getString("by"), Integer.parseInt(res.getString("marks")), Integer.parseInt(res.getString("total")));
                                        classTestDataList.add(classTestData);
                                    }
                                    classTestAdapter.notifyDataSetChanged();
                                } else {
                                    String msg = jsonObject.getString("message");
//                                    tv.setText(msg);
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
                                Toast.makeText(classTest.this, "Oops. Timeout error!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            objectRequest.setRetryPolicy(policy);

            objectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {
                    Toast.makeText(getApplicationContext(),"Please reopen this Page",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(objectRequest);

        }
        else
            Log.i("Error:-","Something went wrong");
//            tv.setText("Uh-oh Something Went Wrong");

    }


}



