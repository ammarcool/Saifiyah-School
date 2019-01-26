package com.ammar.saifiyahschool.teachers.AddClassTest;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class viewClassTest extends Fragment {

    View v;
    private String type,id,ip,URL;
    Spinner viewclassSpinner;
    Spinner viewsubjectSpinner;
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
        subject_url = "http://"+ip+"/school_cms/Schedules/getClasses.json";
        subject_Name_url = "http://"+ip+"/school_cms/Schedules/getSubjects.json";
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
        params.put("id", id);
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

                            OurclassTestData = new viewCTData(Integer.parseInt(res.getString("day")),res.getString("month"),res.getString("subject"),Integer.parseInt(res.getString("total_marks")),res.getString("class"),res.getString("id"));
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

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        viewRequest.setRetryPolicy(policy);

        viewRequest.setRetryPolicy(new RetryPolicy() {
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

        requestQueue.add(viewRequest);
    }

    private void loadClassSubject() {

        final List<classSubjectData> classSubjectDataList = new ArrayList<>();
        final ArrayList<String> showMe = new ArrayList<>();

        final String staff_id = sharedPreferences.getString("id",null);

        final Map<String, String> params = new HashMap();
        params.put("staff_id", staff_id);
        JSONObject myParams = new JSONObject(params);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, subject_url,myParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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

                                    Map<String, String> param = new HashMap();
                                    param.put("section_id", String.valueOf(subPosition));
                                    param.put("staff_id", staff_id);
                                    final JSONObject parameters = new JSONObject(param);

                                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                                            Request.Method.POST, subject_Name_url, parameters, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            JSONObject mySecondJsonObj = null;
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.toString());
                                                String success = jsonObject.getString("success");
                                                JSONObject mysubName = null;
                                                String sub_id = null;
                                                if (success.equals("true")) {
                                                    mySecondJsonObj = jsonObject.getJSONObject("response");
                                                    mySubName.clear();
                                                    showMe.clear();

                                                    Iterator<String> myIterator = mySecondJsonObj.keys();
                                                    while (myIterator.hasNext()) {
//                                                        mysubName = (JSONObject) myjsonArray.get(s);

                                                        sub_id = myIterator.next();
                                                        String subName = mySecondJsonObj.optString(sub_id);

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
                                            Toast.makeText(getActivity(),"Please reopen this Page",Toast.LENGTH_LONG).show();
                                        }
                                    });

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

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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

        requestQueue.add(jsonObjectRequest);

    }

}
