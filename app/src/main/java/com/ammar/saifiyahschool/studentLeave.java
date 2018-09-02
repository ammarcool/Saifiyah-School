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
import java.util.List;
import java.util.Map;

public class studentLeave extends AppCompatActivity {

    private ArrayList<StudentLeaveData> studentLeaveDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentLeaveAdapter studentLeaveAdapter;
    private String type,id,ip,URL,class_id;
    private TextView tv;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave);

        recyclerView=(RecyclerView)findViewById(R.id.studentLeave_recyclerview);

        studentLeaveAdapter = new StudentLeaveAdapter(this,studentLeaveDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(studentLeaveAdapter);

        addStudentLeave();
    }

    private void addStudentLeave() {

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        class_id = sharedPreferences.getString("student_class_id",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        if(!TextUtils.isEmpty(class_id) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(ip))
        {
            URL = "http://" + ip + "/school_cms/studentsLeave/studentLeaves.json";

            Map<String, String> params = new HashMap();
            params.put("student_id", id);
            params.put("student_class_id", class_id);
            JSONObject parameters = new JSONObject(params);
            Log.e("param",parameters.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(studentLeave.this);

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

                                    String months[] = {"January", "February", "March", "April",
                                            "May", "June", "July", "August", "September",
                                            "October", "November", "December"};

                                    StudentLeaveData studentLeaveData;
                                    for(int i = 0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject res = (JSONObject) jsonArray.get(i);
                                        Log.e("data",res.toString());

                                        studentLeaveData = new StudentLeaveData(i+1,months[Integer.parseInt(res.getString("month"))-1],Integer.parseInt(res.getString("number_of_leaves")));
                                        studentLeaveDataList.add(studentLeaveData);

                                    }
                                    studentLeaveAdapter.notifyDataSetChanged();

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
                                Toast.makeText(studentLeave.this, "Oops. Timeout error!", Toast.LENGTH_SHORT).show();
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
        else
            tv.setText("Uh-oh Something Went Wrong");

    }
}
