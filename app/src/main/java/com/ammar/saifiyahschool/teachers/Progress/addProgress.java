package com.ammar.saifiyahschool.teachers.Progress;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.teachers.AddClassTest.addCTMarksData;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class addProgress extends Fragment {

    View v;
    Spinner chooseClass;
    Spinner chooseStudent;
    RequestQueue requestQueue;
    Button submitProgress;
    EditText progressReason;
    RadioButton rewardRadio,complainRadio;
    ArrayList<String> mySubName = new ArrayList<>();
    List<String> StRollNo = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;

    String subject_url ;
    String student_name_URL ;
    String submit_progress_URL ;

    public addProgress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_progress, container, false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        subject_url = "http://"+ip+"/school_cms/Schedules/getClasses.json";
        student_name_URL = "http://"+ip+"/school_cms/students/getStudents.json";
        submit_progress_URL = "http://"+ip+"/school_cms/Progresses/addProgress.json";

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        chooseClass = v.findViewById(R.id.chooseClass);
        chooseStudent = v.findViewById(R.id.chooseStudent);
        requestQueue = Volley.newRequestQueue(getActivity());
        submitProgress = v.findViewById(R.id.submitProgress);
        progressReason = v.findViewById(R.id.progressReason);
        rewardRadio = v.findViewById(R.id.rewardRadio);
        complainRadio = v.findViewById(R.id.complainRadio);

        chooseStudentSpinner();
        return v;
    }

    private void chooseStudentSpinner() {

        final List<classSubjectData> classSubjectDataList = new ArrayList<>();
        final ArrayList<String> showMe = new ArrayList<>();

        final String staff_id = sharedPreferences.getString("id",null);

        final Map<String, String> params = new HashMap();
        params.put("staff_id", staff_id);
        JSONObject myParams = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, subject_url,myParams, new Response.Listener<JSONObject>() {
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

                            classSubjectDataList.add(new classSubjectData(classId, className));

                            final classNameAdapter classNameAdapter = new classNameAdapter(getActivity(),R.layout.class_name,R.id.className,classSubjectDataList);
                            chooseClass.setAdapter(classNameAdapter);

                            chooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final classSubjectData subjectName = classNameAdapter.getItem(position);
                                    final int classPosition = subjectName.getClassId();

                                    Map<String, String> params = new HashMap();
                                    params.put("id", String.valueOf(classPosition));
                                    final JSONObject parameters = new JSONObject(params);

                                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                                            Request.Method.POST, student_name_URL, parameters, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            JSONArray myjsonArray = null;
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.toString());
                                                String success = jsonObject.getString("success");
                                                JSONObject mysubName = null;
                                                if (success.equals("true")) {
                                                    myjsonArray = jsonObject.getJSONArray("response");
                                                    mySubName.clear();
                                                    StRollNo.clear();
                                                    for (int s=0;s<myjsonArray.length();s++) {
                                                        mysubName = (JSONObject) myjsonArray.get(s);

                                                        String studId = mysubName.getString("id");
                                                        String studName = mysubName.getString("name");

                                                        StRollNo.add(studId);
                                                        mySubName.add(studName);
                                                        chooseStudent.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mySubName));

                                                    }
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

                                    /*Get student Id */
                                    chooseStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {

                                            Log.i("My Subject Position-->", StRollNo.get(position));

                                            Log.i("My classID--->", String.valueOf(classPosition));

                                            /* -----------------------------------Start submit the Progress---------------------------*/
                                            submitProgress.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {


                                                    Map<String, String> params = new HashMap();

                                                    if(complainRadio.isChecked()){
                                                        params.put("is_reward",String.valueOf(0));
                                                    }else{
                                                        params.put("is_reward",String.valueOf(1));
                                                    }
                                                    params.put("student_class_id", String.valueOf(classPosition));
                                                    params.put("student_id",StRollNo.get(position));
                                                    params.put("created_by", staff_id);
                                                    params.put("title",progressReason.getText().toString());

                                                    JSONObject parameters = new JSONObject(params);



                                                    JsonObjectRequest submitMarks = new JsonObjectRequest(
                                                            Request.Method.POST, submit_progress_URL, parameters, new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {

                                                            String CTjsonArray = null;
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(response.toString());
                                                                String success = jsonObject.getString("success");
                                                                if (success.equals("true")) {
                                                                    CTjsonArray = jsonObject.getString("message");

                                                                    Toast.makeText(getContext(),CTjsonArray.toString(),Toast.LENGTH_LONG).show();

                                                                    Log.i("Response Me ----->", String.valueOf(CTjsonArray));

                                                                }else {
                                                                    String msg = jsonObject.getString("message");
                                                                    Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                Toast.makeText(getContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    }

                                                    );
                                                    requestQueue.add(submitMarks);

                                                }
                                            });

                                            /* ------------------------------------End submit the Class test -----------------------------*/

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

//                                  /*End sub Id */

                                    requestQueue.add(jsonObjectRequest1);
//                                    requestQueue.add(studentNameRequest);

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



