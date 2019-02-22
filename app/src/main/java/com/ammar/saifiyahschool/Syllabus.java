package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

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
    ArrayList<String> subjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        Toolbar toolbar = findViewById(R.id.syllabusToolbar);
        toolbar.setTitle("Your Syllabus");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        id = sharedPreferences.getString("id",null);

        requestQueue = Volley.newRequestQueue(Syllabus.this);
        subjectArrayList = new ArrayList<>();
//        Bundle bundle = new Bundle();
//        bundle.putString("my_key", "My String");
//        EnglishFreg myFrag = new EnglishFreg();
//        myFrag.setArguments(bundle);
//        getFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, myFrag, "myFrag")
//                .commit();

        initViews();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                Log.i("Response-->", response.toString());
                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        JSONObject res = null;

                        int j = 0;

                        for(int i = 0; i < jsonArray.length(); i++)
                        {

                            res = (JSONObject) jsonArray.get(i);

                            if (res.getString("subject").equals(sharedPreferences.getString("subject",null))){
                                    continue;

                            }else {

                                mTabLayout.addTab(mTabLayout.newTab().setText(res.getString("subject")));

                                subjectArrayList.add(res.getString("subject"));

                                saveArrayList(subjectArrayList,"Ammar");
//
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString("subject",res.getString("subject"));
//                                Log.i("Subject-->",res.getString("subject"));
                                edit.apply();
                            }


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

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        studentSyllabusTab.setRetryPolicy(policy);

        studentSyllabusTab.setRetryPolicy(new RetryPolicy() {
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

        requestQueue.add(studentSyllabusTab);

    }
    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

}
