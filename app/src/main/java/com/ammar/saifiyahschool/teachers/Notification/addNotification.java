package com.ammar.saifiyahschool.teachers.Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class addNotification extends AppCompatActivity {

    Spinner chooseClassForNotification;
    EditText notificationTitle;
    EditText enterNotificationMessage;
    Button submitNotification;

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    String submit_Notification_URL,subject_url;
    String staff_id,type,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        chooseClassForNotification = findViewById(R.id.chooseClassForNotification);
        notificationTitle = findViewById(R.id.notificationTitle);
        enterNotificationMessage = findViewById(R.id.enterNotificationMessage);
        submitNotification = findViewById(R.id.submitNotification);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        requestQueue = Volley.newRequestQueue(addNotification.this);

        subject_url = "http://"+ip+"/school_cms/student-classes/getClasses.json";
        submit_Notification_URL = "";




                final List<classSubjectData> classSubjectDataList = new ArrayList<>();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, subject_url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("my Response",response.toString());

                        JSONArray jsonArray = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String success = jsonObject.getString("success");
                            if (success.equals("true")) {
                                jsonArray = jsonObject.getJSONArray("response");
                                JSONObject res = null;
                                String className = null;
                                Integer classId = null;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    res = (JSONObject) jsonArray.get(i);

                                    className = res.getString("name");
                                    classId= res.getInt("id");

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
                                            params.put("id", String.valueOf(classPosition));
                                            params.put("",notificationTitle.getText().toString());
                                            params.put("",enterNotificationMessage.getText().toString());
                                            final JSONObject parameters = new JSONObject(params);

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
}
