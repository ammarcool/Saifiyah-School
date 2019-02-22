package com.ammar.saifiyahschool;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishFreg extends Fragment {

    View v;
    RecyclerView recyclerView ;
    private ArrayList<SyllabusData> syllabusDataArrayList = new ArrayList<>() ;
    RequestQueue requestQueue;
    syllabusAdapter syllabusAdapter;
    private SharedPreferences sharedPreferences;
    private String type,id,ip,URL,student_class_id;


    public EnglishFreg() {
        // Required empty public constructor
    }

    public static  EnglishFreg newInstance() {
        return new EnglishFreg();
    }

    LinearLayoutManager OurlinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_english_freg, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        syllabusAdapter = new syllabusAdapter(getContext(),syllabusDataArrayList);
        recyclerView = v.findViewById(R.id.English_recyclerView);
        OurlinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(OurlinearLayoutManager);
        recyclerView.setAdapter(syllabusAdapter);
        initView();



        return v;
    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toast.makeText(getActivity(),"Results---> "+getArguments().getInt("position"),Toast.LENGTH_LONG).show();


        Log.i("my Position --->",String.valueOf(getArguments().getInt("position")));
    }


    private void initView() {
        //Toast.makeText(getActivity(),"Results--->"+getArguments().getInt("position"),Toast.LENGTH_LONG).show();

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        student_class_id = sharedPreferences.getString("student_class_section_id",null);

        /***********Start Jsonvolley ***********/

        String ViewSyllabus_URL = "http://"+ip+"/school_cms/syllabus/viewSyllabusStudent.json";
        Map<String, String> params = new HashMap();
        params.put("id", student_class_id);

        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest myProgressRequest = new JsonObjectRequest(Request.Method.POST, ViewSyllabus_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response-->", response.toString());
                JSONArray jsonArray = null;
                JSONArray nestedJsonArray = null;
                String storeId ,topicId;
                int position = getArguments().getInt("position");
                Log.i("SubNames===>", getArrayList("Ammar").get(position));
                Bundle bundle = getArguments();
                String subj = bundle.getString("sub");
                Log.i("Subj-->", String.valueOf(subj));
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        JSONObject menuPosition = jsonArray.getJSONObject(position);
//                        nestedJsonArray = menuPosition.getJSONArray("syllabus_rows");

                        SyllabusData syllabusData = null;
                        SyllabusData ExamsData = null;
                        Log.i("Position===>", String.valueOf(menuPosition.length()));
                        Log.i("DATA--->",String.valueOf(sharedPreferences.getString("subject",null)));
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject examinationName = (JSONObject) jsonArray.get(i);
                            nestedJsonArray = examinationName.getJSONArray("syllabus_rows");

                            if (examinationName.getString("subject").equals(getArrayList("Ammar").get(position))) {

                                String examName = examinationName.getString("exam_name");
                                ExamsData = new SyllabusData(examName, SyllabusData.StudentItemType.ONE_ITEM);
                                syllabusDataArrayList.add(ExamsData);

                                for (int j = 0; j < nestedJsonArray.length(); j++) {

                                    JSONObject nestedJson = (JSONObject) nestedJsonArray.get(j);

                                    syllabusData = new SyllabusData(nestedJson.getString("edited_month"), nestedJson.getString("edited_day"), nestedJson.getString("id"), nestedJson.getString("topic"), nestedJson.getString("status"), SyllabusData.StudentItemType.TWO_ITEM);
                                    syllabusDataArrayList.add(syllabusData);

                                }
                            }else {
                                continue;
                            }


                            syllabusAdapter.notifyDataSetChanged();
                        }

                    } else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getContext(),String.valueOf(msg),Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }
        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myProgressRequest.setRetryPolicy(policy);

        myProgressRequest.setRetryPolicy(new RetryPolicy() {
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
                Toast.makeText(getActivity(),"Please reopen this Page",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(myProgressRequest);


        /************* End JsonVolley *****************/


    }
}
