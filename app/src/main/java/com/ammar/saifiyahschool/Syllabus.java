package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Map;

public class Syllabus extends AppCompatActivity {

    ViewPager myviewPager;
    TabLayout mTabLayout;
    RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private String type,id,ip,URL,student_class_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        Toolbar toolbar = findViewById(R.id.syllabusToolbar);
        toolbar.setTitle("Your Syllabus");
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        id = sharedPreferences.getString("id",null);

        requestQueue = Volley.newRequestQueue(Syllabus.this);
        initViews();

    }

    private void initViews() {

        myviewPager = findViewById(R.id.myViewpager);
        mTabLayout = findViewById(R.id.myTabLayout);
        myviewPager.setOffscreenPageLimit(3);
        myviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setDynamicFragmentToTabLayout();
    }

    private void setDynamicFragmentToTabLayout() {

        ip = sharedPreferences.getString("ip",null);
        student_class_id = sharedPreferences.getString("student_class_id",null);

        String student_syllabus_URL = "http://"+ip+"/school_cms/syllabus/viewSyllabusStudent.json";

        Map<String, String> params = new HashMap();
        params.put("id", student_class_id);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest studentSyllabusTab = new JsonObjectRequest(Request.Method.POST, student_syllabus_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        JSONObject res = null;

                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            res = (JSONObject) jsonArray.get(i);
                            mTabLayout.addTab(mTabLayout.newTab().setText(res.getString("subject")));
                        }
                        DynamicFragmentAdapter mDynamicFragmentAdapter = new DynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(),getApplicationContext());
                        myviewPager.setAdapter(mDynamicFragmentAdapter);
                        myviewPager.setCurrentItem(0);

                    } else {
                        Toast.makeText(getApplicationContext(),"nothing found",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(studentSyllabusTab);

    }

}
