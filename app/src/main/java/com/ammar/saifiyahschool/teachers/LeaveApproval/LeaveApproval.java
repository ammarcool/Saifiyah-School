package com.ammar.saifiyahschool.teachers.LeaveApproval;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.NoticeBoardAdapter;
import com.ammar.saifiyahschool.NoticeBoardData;
import com.ammar.saifiyahschool.R;
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

public class LeaveApproval extends AppCompatActivity {

    private ArrayList<LeaveApprovalData> leaveApprovalDataArrayList = new ArrayList<>();
    private RecyclerView leave_approval_recyclerview;
    private LeaveApprovalAdapter leaveApprovalAdapter;
    private String type,id,ip,URL,class_id;
    private TextView tv;
    private SharedPreferences sharedPreferences;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_approval);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Approve Staff Leaves");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        leave_approval_recyclerview=(RecyclerView)findViewById(R.id.leave_approval_recyclerview);

        leaveApprovalAdapter = new LeaveApprovalAdapter(leaveApprovalDataArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        leave_approval_recyclerview.setLayoutManager(mLayoutManager);
        leave_approval_recyclerview.setAdapter(leaveApprovalAdapter);

     LeaveApprovalData  leaveApprovalData = new LeaveApprovalData("20","feb","Ammar Miyaji","casual leave","14-12-2018","20-20-2018","i'm suffering from Fever","0");
        leaveApprovalDataArrayList.add(leaveApprovalData);

        leaveApprovalData = new LeaveApprovalData("10","feb","Ammar Miyaji","casual leave","14-12-2018","20-20-2018","i'm suffering from Fever","1");
        leaveApprovalDataArrayList.add(leaveApprovalData);

        leaveApprovalData = new LeaveApprovalData("10","feb","Ammar Nuruddin Miyaji","casual leave","14-12-2018","20-20-2018","i'm suffering from Fever so i can't attend the class please try to understand and resolve this problem.thankyou soo much","2");
        leaveApprovalDataArrayList.add(leaveApprovalData);

//        leaveApprovalList();
        
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void leaveApprovalList() {

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

                                LeaveApprovalData leaveApprovalData;
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject res = (JSONObject) jsonArray.get(i);
                                    Log.e("data",res.toString());

                                    leaveApprovalData = new LeaveApprovalData("20","feb","Ammar Miyaji","casual leave","14-12-2018","20-20-2018","i'm suffering from Fever","0");
                                    leaveApprovalDataArrayList.add(leaveApprovalData);

                                }
                                leaveApprovalAdapter.notifyDataSetChanged();

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
