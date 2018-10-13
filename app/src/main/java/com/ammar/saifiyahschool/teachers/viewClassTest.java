package com.ammar.saifiyahschool.teachers;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.Recycler;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class viewClassTest extends Fragment {

    View v;
    private String type,id,ip,URL;
    Spinner viewclassSpinner;
    Spinner viewsubjectSpinner;
    Integer staffId= 2;
    private String subject_url;
    private String subject_Name_url;
    private String viewCT_URL;
    private SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    ArrayList<String> mySubName = new ArrayList<>();


    private ArrayList<viewCTData> viewCTDataArrayList = new ArrayList<>();
    private RecyclerView viewCTRecyclerView;
    viewClassTestAdapter viewClassTestAdapter;
    LinearLayoutManager mylinearLayoutManager;



    public viewClassTest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        subject_url = "http://"+ip+"/school_cms/student-classes/getClasses.json";
        subject_Name_url = "http://"+ip+"/school_cms/ClassTests/getSubjects.json";
        viewCT_URL = "http://"+ip+"/school_cms/ClassTests/viewClassTest.json";
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_view_class_test, container, false);
        viewclassSpinner = v.findViewById(R.id.viewclassSpinner);
        viewsubjectSpinner = v.findViewById(R.id.viewsubjectSpinner);
        requestQueue = Volley.newRequestQueue(getActivity());

        viewClassTestAdapter = new viewClassTestAdapter(getActivity(),viewCTDataArrayList);
        viewCTRecyclerView = v.findViewById(R.id.TeacherviewCTRecyclerView);
        mylinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        viewCTRecyclerView.setLayoutManager(mylinearLayoutManager);
        viewCTRecyclerView.setAdapter(viewClassTestAdapter);


        loadClassSubject();

        viewCT();
        return v;

    }


    private void viewCT() {

        Map<String, String> params = new HashMap();
        params.put("id", id.toString());
        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest viewRequest = new JsonObjectRequest(Request.Method.POST, viewCT_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        viewCTData OurclassTestData = null;
                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject res = (JSONObject) jsonArray.get(i);

                            OurclassTestData = new viewCTData(Integer.parseInt(res.getString("day")),res.getString("month"),res.getString("subject"),Integer.parseInt(res.getString("total_marks")),res.getString("class"),null);
                            viewCTDataArrayList.add(OurclassTestData);
                        }

                        viewClassTestAdapter.notifyDataSetChanged();
                    } else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getContext(),String.valueOf(msg),Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),String.valueOf(e),Toast.LENGTH_LONG).show();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),String.valueOf(error),Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(viewRequest);
    }

    private void loadClassSubject() {

        final List<classSubjectData> classSubjectDataList = new ArrayList<>();
        final ArrayList<String> showMe = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, subject_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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

//                                classNameList.add(String.valueOf(className));
//                                classNameList.add(String.valueOf(classId));

                            classSubjectDataList.add(new classSubjectData(classId, className));

                            final classNameAdapter classNameAdapter = new classNameAdapter(getActivity(),R.layout.class_name,R.id.className,classSubjectDataList);
                            viewclassSpinner.setAdapter(classNameAdapter);

                            viewclassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final classSubjectData subjectName = classNameAdapter.getItem(position);
//                                    List<Integer> subPosition = Collections.singletonList(subjectName.getClassId());
                                    final int subPosition = subjectName.getClassId();

                                    Map<String, String> params = new HashMap();
                                    params.put("id", String.valueOf(subPosition));
                                    final JSONObject parameters = new JSONObject(params);

                                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                                            Request.Method.POST, subject_Name_url, parameters, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            JSONArray myjsonArray = null;
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.toString());
                                                String success = jsonObject.getString("success");
                                                JSONObject mysubName = null;
                                                String sub_id = null;
                                                if (success.equals("true")) {
                                                    myjsonArray = jsonObject.getJSONArray("response");
                                                    mySubName.clear();
                                                    showMe.clear();
                                                    for (int s=0;s<myjsonArray.length();s++) {
                                                        mysubName = (JSONObject) myjsonArray.get(s);

                                                        String subName = mysubName.getString("name");
                                                        sub_id = mysubName.getString("id");

                                                        final classSubjectData mysubjectName = new classSubjectData(subName, sub_id);
//                                                        classSubjectDataList1.add(mysubjectName);

                                                        String mylist = mysubjectName.getSubjectName();
                                                        final String showId = mysubjectName.getSubjectId();
                                                        mySubName.add(mylist);
                                                        viewsubjectSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mySubName));


                                                        showMe.add(showId);
                                                    }
//                                                    mySubName = new ArrayList<>();
                                                }else {
                                                    String msg = jsonObject.getString("message");
                                                    Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
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

                                    viewsubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                            Log.i("My Subject Position-->", showMe.get(position));

                                            Log.i("My classID--->", String.valueOf(subPosition));






                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

//                                  /*End sub Id */

                                    requestQueue.add(jsonObjectRequest1);

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
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }

}
