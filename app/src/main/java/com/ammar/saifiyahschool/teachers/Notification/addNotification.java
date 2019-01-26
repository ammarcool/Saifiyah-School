package com.ammar.saifiyahschool.teachers.Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.teachers.AddClassTest.classNameAdapter;
import com.ammar.saifiyahschool.teachers.AddClassTest.classSubjectData;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class addNotification extends AppCompatActivity {

    Spinner chooseClassForNotification;
    EditText notificationTitle;
    EditText enterNotificationMessage;
    Button submitNotification;

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    String submit_Notification_URL,subject_url;
    String staff_id,type,ip;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Send Notification");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chooseClassForNotification = findViewById(R.id.chooseClassForNotification);
        notificationTitle = findViewById(R.id.notificationTitle);
        enterNotificationMessage = findViewById(R.id.enterNotificationMessage);
        submitNotification = findViewById(R.id.submitNotification);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        requestQueue = Volley.newRequestQueue(addNotification.this);

        subject_url = "http://"+ip+"/school_cms/Schedules/getClasses.json";
        submit_Notification_URL = "http://"+ip+"/school_cms/Notifications/classNotification.json";

        final String staff_id = sharedPreferences.getString("id",null);

        final Map<String, String> params = new HashMap();
        params.put("staff_id", staff_id);
        JSONObject myParams = new JSONObject(params);


                final List<classSubjectData> classSubjectDataList = new ArrayList<>();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST, subject_url,myParams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("my Response",response.toString());

                        JSONObject oneJsonObj = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String success = jsonObject.getString("success");
                            if (success.equals("true")) {
                                oneJsonObj = jsonObject.getJSONObject("response");
                                JSONObject res = null;
                                String className = null;
                                Integer classId = null;

                                Iterator<String> iterator = oneJsonObj.keys();

                                while (iterator.hasNext()) {
//                            res = (JSONObject) oneJsonObj.getString(i);

                                    classId= Integer.valueOf(iterator.next());
                                    className = oneJsonObj.optString(String.valueOf(classId));

                                    Log.i("className-->",className + classId);

                                    classSubjectDataList.add(new classSubjectData(classId, className));

                                    final classNameAdapter classNameAdapter = new classNameAdapter(addNotification.this,R.layout.class_name,R.id.className,classSubjectDataList);
                                    chooseClassForNotification.setAdapter(classNameAdapter);

                                    chooseClassForNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            final classSubjectData subjectName = classNameAdapter.getItem(position);
                                            final int classPosition = subjectName.getClassId();

                                            submitNotification.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                            Map<String, String> params = new HashMap();
                                            params.put("staff_id", staff_id);
                                            params.put("student_class_section_id", String.valueOf(classPosition));
                                            params.put("title",notificationTitle.getText().toString());
                                            params.put("message",enterNotificationMessage.getText().toString());
                                            params.put("type","Class");
                                            final JSONObject parameters = new JSONObject(params);

                                            Toast.makeText(addNotification.this,"Submitted" + params,Toast.LENGTH_LONG).show();

                                            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                                                    Request.Method.POST, submit_Notification_URL, parameters, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    String mResponseMsg = null;
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response.toString());
                                                        String success = jsonObject.getString("success");
                                                        if (success.equals("true")) {
                                                            mResponseMsg = jsonObject.getString("response");

                                                            Toast.makeText(getApplicationContext(),mResponseMsg,Toast.LENGTH_LONG).show();

                                                        }else {
                                                            String msg = jsonObject.getString("message");
                                                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            }
                                            );

                                                    int socketTimeout = 30000;//30 seconds - change to what you want
                                                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                                                    jsonObjectRequest1.setRetryPolicy(policy);

                                                    jsonObjectRequest1.setRetryPolicy(new RetryPolicy() {
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
                                            requestQueue.add(jsonObjectRequest1);
                                        }
                                    });
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }

                                    });
                                }
                            } else {
                                String msg = jsonObject.getString("message");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                );
                requestQueue.add(jsonObjectRequest);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
