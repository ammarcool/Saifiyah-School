package com.ammar.saifiyahschool.teachers.AddClassTest;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class addClassTestMarks extends Fragment {

    View v;
    Spinner stateSpinner;
    Spinner citiesSpinner;
    private ProgressDialog pDialog;
    private String type,staff_id,ip,URL;
    private SharedPreferences sharedPreferences;
    private String subject_url;
    private String subject_Name_url;
    private String Student_Name_url;
    private String submitclassTest_url;
    RequestQueue requestQueue;
    ArrayList<String> mySubName = new ArrayList<>();

    List<String> submitCTMarksnew = new ArrayList<String>();
    List<String> StRollNo = new ArrayList<String>();

    Button submitCTest;
    EditText enterStudentMarks;
    EditText myTestDate;
    EditText totalMarks;
    TextView studentNo;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private ArrayList<addCTMarksData> addCTMarksDataArrayList = new ArrayList<>();
    private RecyclerView addCTRecyclerView;
    private addCTMarksAdapter myaddCTMarksAdapter = null;
    LinearLayoutManager linearLayoutManager;

    public addClassTestMarks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        subject_url = "http://"+ip+"/school_cms/Schedules/getClasses.json";
        subject_Name_url = "http://"+ip+"/school_cms/Schedules/getSubjects.json";
        Student_Name_url = "http://"+ip+"/school_cms/students/getStudents.json";
        submitclassTest_url= "http://"+ip+"/school_cms/ClassTests/addClassTest.json";

        v = inflater.inflate(R.layout.fragment_add_class_test_marks, container, false);
        stateSpinner = v.findViewById(R.id.classSpinner);
        citiesSpinner = v.findViewById(R.id.subjectSpinner);
        requestQueue = Volley.newRequestQueue(getActivity());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        submitCTest = v.findViewById(R.id.submitCTest);
        enterStudentMarks = v.findViewById(R.id.enterStudentMarks);
        myTestDate = v.findViewById(R.id.myTestDate);
        myTestDate.setInputType(InputType.TYPE_NULL);
        totalMarks = v.findViewById(R.id.totalMarks);
        studentNo = v.findViewById(R.id.studentNo);

        myaddCTMarksAdapter = new addCTMarksAdapter(addCTMarksDataArrayList,getContext());
        addCTRecyclerView = v.findViewById(R.id.addClassTestRecycler);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        addCTRecyclerView.setLayoutManager(linearLayoutManager);
        addCTRecyclerView.setAdapter(myaddCTMarksAdapter);

        myTestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                myTestDate.setText(date);
            }
        };

        loadStateCityDetails();
        return  v;
    }



    private void loadStateCityDetails() {
        final List<classSubjectData> classSubjectDataList = new ArrayList<>();
        final List<classSubjectData> classSubjectDataList1 = new ArrayList<>();
        final ArrayList<String> showMe = new ArrayList<>();

        Map<String, String> params = new HashMap();
        params.put("staff_id", staff_id);
        JSONObject myParams = new JSONObject(params);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                        String my_sub_id = null;

                        Iterator<String> iterator = oneJsonObj.keys();

                        while (iterator.hasNext()) {
//                            res = (JSONObject) oneJsonObj.getString(i);

                            classId= Integer.valueOf(iterator.next());
                            className = oneJsonObj.optString(String.valueOf(classId));



//                                classNameList.add(String.valueOf(className));
//                                classNameList.add(String.valueOf(classId));

                            classSubjectDataList.add(new classSubjectData(classId, className));


                            final classNameAdapter classNameAdapter = new classNameAdapter(getActivity(),R.layout.class_name,R.id.className,classSubjectDataList);
                            stateSpinner.setAdapter(classNameAdapter);

                            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final classSubjectData subjectName = classNameAdapter.getItem(position);
//                                    List<Integer> subPosition = Collections.singletonList(subjectName.getClassId());
                                    final int subPosition = subjectName.getClassId();

                                    Map<String, String> param = new HashMap();
                                    param.put("section_id", String.valueOf(subPosition));
                                    param.put("staff_id", staff_id);
                                    final JSONObject subjectParams = new JSONObject(param);

                                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                                            Request.Method.POST, subject_Name_url, subjectParams, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            JSONObject mySecondJsonObj = null;
                                            List<String> classNameList = new ArrayList<>();
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
                                                        Log.i("my Subject====>",sub_id+" "+subName);

                                                        final classSubjectData mysubjectName = new classSubjectData(subName, sub_id);
//                                                        classSubjectDataList1.add(mysubjectName);

                                                        String mylist = mysubjectName.getSubjectName();
                                                        final String showId = mysubjectName.getSubjectId();
                                                        mySubName.add(mylist);
                                                        citiesSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mySubName));


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

                                    Map<String, String> params = new HashMap();
                                    params.put("id", String.valueOf(subPosition));
                                    final JSONObject parameters = new JSONObject(params);

                                    /* Start Students Name Json Volley  */
                                    JsonObjectRequest studentNameRequest = new JsonObjectRequest(
                                            Request.Method.POST, Student_Name_url, parameters, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            JSONArray myjsonArray = null;
                                            List<String> classNameList = new ArrayList<>();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.toString());
                                                String success = jsonObject.getString("success");
                                                JSONObject myStudStuff = null;
                                                if (success.equals("true")) {
                                                    myjsonArray = jsonObject.getJSONArray("response");

                                                    addCTMarksData newaddCTMarksData = null;
                                                    addCTMarksDataArrayList.clear();
                                                    for (int s=0;s<myjsonArray.length();s++){
                                                        myStudStuff = (JSONObject) myjsonArray.get(s);

                                                        String studId = myStudStuff.getString("id");
                                                        String studName = myStudStuff.getString("name");
//                                                        String mydata = newaddCTMarksData.setEnterCT(String.valueOf(s));

                                                        StRollNo.add(studId);
                                                        newaddCTMarksData = new addCTMarksData(studId,studName);
                                                        addCTMarksDataArrayList.add(newaddCTMarksData);


                                                    }

                                                    myaddCTMarksAdapter.notifyDataSetChanged();

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
                                    /* End Student Name Json Volley */

                                    /*Get sub Id */
                                    citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {

                                            Log.i("My Subject Position-->", showMe.get(position));

                                            Log.i("My classID--->", String.valueOf(subPosition));

                                            /* -----------------------------------Start submit the class test---------------------------*/

//                                            final AwesomeValidation awesomeValidation = new AwesomeValidation(COLORATION);

//                                            awesomeValidation.addValidation(totalMarks,totalMarks,"Please Enter Total Marks");
//                                            awesomeValidation.addValidation(myTestDate,myTestDate,"Please Add ClassTest Date");

                                            submitCTest.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    if (!TextUtils.isEmpty(totalMarks.getText().toString()) && !TextUtils.isEmpty(myTestDate.getText().toString()) ) {
                                                        Map<String, String> params = new HashMap();
                                                        params.put("student_class_section_id", String.valueOf(subPosition));
                                                        params.put("subject_id", showMe.get(position));
                                                        params.put("total_marks", totalMarks.getText().toString());
                                                        params.put("created_on", myTestDate.getText().toString());
                                                        params.put("created_by", staff_id);

                                                        JSONArray ourArray = new JSONArray();
                                                        JSONObject ourjsonObject = new JSONObject();
                                                        JSONObject mJsonObject = null;

                                                        for (int i = 0; i < addCTMarksDataArrayList.size(); i++) {

                                                            submitCTMarksnew.add(myaddCTMarksAdapter.addCTMarksDataArrayList.get(i).getEnterCT().toString());

                                                            try {
                                                                mJsonObject = new JSONObject();

                                                                mJsonObject.put("student_id", StRollNo.get(i));
                                                                mJsonObject.put("marks", submitCTMarksnew.get(i));

                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                            ourArray.put(mJsonObject);
                                                        }

                                                        //params.put("class_test_rows", ourArray.toString());
                                                        Log.i("class_test_rows", ourArray.toString());
                                                        submitCTMarksnew.clear();
                                                        StRollNo.clear();

                                                        JSONObject parameters = new JSONObject(params);

                                                        try {
                                                            parameters.put("class_test_rows", ourArray);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        JsonObjectRequest submitMarks = new JsonObjectRequest(
                                                                Request.Method.POST, submitclassTest_url, parameters, new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {

                                                                String CTjsonArray = null;
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response.toString());
                                                                    String success = jsonObject.getString("success");
                                                                    if (success.equals("true")) {
                                                                        CTjsonArray = jsonObject.getString("message");

                                                                        Toast.makeText(getContext(), CTjsonArray.toString(), Toast.LENGTH_LONG).show();

                                                                        Log.i("Response Me ----->", String.valueOf(CTjsonArray));

                                                                    } else {
                                                                        String msg = jsonObject.getString("message");
                                                                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
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
                                                        submitMarks.setRetryPolicy(policy);

                                                        submitMarks.setRetryPolicy(new RetryPolicy() {
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

                                                        requestQueue.add(submitMarks);

                                                    }else {
                                                        Toast.makeText(getContext(),"Please Fill Submit Date or Total Marks Box",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                            /* ------------------------------------End submit the Class test -----------------------------*/

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

                                    socketTimeout = 30000;//30 seconds - change to what you want
                                     policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                                    studentNameRequest.setRetryPolicy(policy);

                                    studentNameRequest.setRetryPolicy(new RetryPolicy() {
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
                                    requestQueue.add(studentNameRequest);

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
