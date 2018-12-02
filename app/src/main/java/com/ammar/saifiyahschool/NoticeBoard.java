package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.Map;

public class NoticeBoard extends AppCompatActivity {

    private ArrayList<NoticeBoardData> noticeBoardDataArrayList = new ArrayList<>();
    private RecyclerView notificationMessage;
    private NoticeBoardAdapter noticeBoardAdapter;
    private String type,id,ip,URL,class_id;
    private TextView tv;
    private SharedPreferences sharedPreferences;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notifications");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        notificationMessage=(RecyclerView)findViewById(R.id.notificationMessage);

        noticeBoardAdapter = new NoticeBoardAdapter(noticeBoardDataArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationMessage.setLayoutManager(mLayoutManager);
        notificationMessage.setAdapter(noticeBoardAdapter);

//        addNotice();

      NoticeBoardData  noticeBoardData = new NoticeBoardData("Ammar Miyaji","10/12/2018","hello bro!thank you soo much");
        noticeBoardDataArrayList.add(noticeBoardData);

        noticeBoardData = new NoticeBoardData("Ammar Miyaji","10/12/2018","hello bro!thank you soo much,for being with me yhou are really amazing i love yhour company");
        noticeBoardDataArrayList.add(noticeBoardData);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addNotice() {

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        class_id = sharedPreferences.getString("student_class_id",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);


        URL = " ";

        Map<String, String> params = new HashMap();
        params.put("", "");
        params.put("student_class_id", "");
        JSONObject parameters = new JSONObject(params);
        Log.e("param",parameters.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);

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

                                NoticeBoardData noticeBoardData;
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject res = (JSONObject) jsonArray.get(i);
                                    Log.e("data",res.toString());

                                    noticeBoardData = new NoticeBoardData("Ammar Miyaji","10/12/2018","hello bro!thank you soo much");
                                    noticeBoardDataArrayList.add(noticeBoardData);

                                }
                                noticeBoardAdapter.notifyDataSetChanged();

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
                            Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);

    }
}
