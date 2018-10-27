package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ammar.saifiyahschool.teachers.AddClassTest.TeacherClassTest;
import com.ammar.saifiyahschool.teachers.LeaveBalance.leaveBalance;
import com.ammar.saifiyahschool.teachers.Progress.progresses;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    private String type,id,ip,URL;
    private TextView tv, name, user_id,house;
    private ImageView profile;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private long backPressedTime;
    private SharedPreferences sharedPreferences;

    private Toolbar toolbar ;

    Button syllabus,classTest,feeButton,notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        notificationButton = (Button)findViewById(R.id.notificationButton);
        syllabus = (Button)findViewById(R.id.syllabusButton);
        classTest =(Button)findViewById(R.id.classTestButton);
        feeButton = (Button)findViewById(R.id.feeButton);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.notes:
                        item.setChecked(true);

                        Intent intent = new Intent(Dashboard.this,TeacherClassTest.class);
                        intent.putExtra("Leaves","no.of leaves");
                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(),"this is notes bro...",Toast.LENGTH_LONG).show();
                        //displaymessage("this is gallery bro...");
                        drawerLayout.closeDrawers();
                        return true;


                    case R.id.leaveBalance:
                        item.setChecked(true);

                        intent = new Intent(Dashboard.this, studentLeave.class);
                        intent.putExtra("Leaves","no.of leaves");
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.results:
                        item.setChecked(true);

                        intent = new Intent(Dashboard.this, Results.class);
                        intent.putExtra("results","myResults");
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.progress:
                        item.setChecked(true);

                        intent = new Intent(Dashboard.this, StudentProgress.class);
                        intent.putExtra("progress","Rewards");
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.changePassword:
                        item.setChecked(true);

                        intent = new Intent(Dashboard.this, progresses.class);
                        intent.putExtra("teacherProgress","addAndView");
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        return true;
                }

                return false;
            }
        });

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        //tv = findViewById(R.id.dashboard_error);
        name = findViewById(R.id.name);
        user_id = findViewById(R.id.userID);
        profile = findViewById(R.id.imageView);

        if(!TextUtils.isEmpty(type) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(ip))
        {
            if(id.equals("0"))
                tv.setText("Hello Admin");
            else {
                if (type.equals("student"))
                    URL = "http://" + ip + "/school_cms/Students/viewApi.json";
                else
                    URL = "http://" + ip + "/school_cms/staffs/viewApi.json";

                Map<String, String> params = new HashMap();
                params.put("id", id);
                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);

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
                                        jsonArray = response.getJSONArray("response");
                                        JSONObject res = (JSONObject) jsonArray.get(0);
                                        if (type.equals("student")) {
                                            JSONObject student_class = res.getJSONObject("student_class");

                                            SharedPreferences.Editor edit = sharedPreferences.edit();
                                            edit.putString("student_class_id", res.getString("student_class_id"));
                                            edit.putString("total_fees", res.getString("total fees"));
                                            edit.apply();

                                            name.setText(res.getString("name").toUpperCase() + "\nClass: " + student_class.getString("name"));

                                        }
                                        else
                                        {
                                            name.setText(res.getString("name").toUpperCase());
                                        }
                                        user_id.setText("ID: " + res.getString("id"));
                                        String path = "http://"+ip+"/school_cms/"+res.getString("image");
                                        Picasso.with(Dashboard.this).load(path).into(profile);
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
                                    Toast.makeText(Dashboard.this, "Oops. Timeout error!", Toast.LENGTH_SHORT).show();
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
        else
            tv.setText("Uh-oh Something Went Wrong");


        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,leaveBalance.class);
                intent.putExtra("LeaveBalance","LeaveBalance");
                startActivity(intent);
            }
        });

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (type.equals("student"))
                    intent = new Intent(Dashboard.this,Syllabus.class);
                else
                    intent = new Intent(Dashboard.this,Syllabus.class);
                intent.putExtra("syllabus","subjects");
                startActivity(intent);
            }
        });

        classTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (type.equals("student"))
                    intent = new Intent(Dashboard.this,classTest.class);
                else
                    intent = new Intent(Dashboard.this,TeacherClassTest.class);
                intent.putExtra("class","marks");
                startActivity(intent);
            }
        });

        feeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,Fees.class);
                intent.putExtra("fees","transaction");
                startActivity(intent);
            }
        });
    }


    private void displaymessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        Log.d("message====>",message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.remove("username");
                edit.remove("password");
                edit.remove("id");
                edit.remove("type");
                edit.remove("student_class_id");
                edit.apply();
                Intent it = new Intent(Dashboard.this, MainActivity.class);
                startActivity(it);
                finish();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            backPressedTime = System.currentTimeMillis();
        }
    }
}
