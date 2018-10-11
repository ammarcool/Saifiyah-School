package com.ammar.saifiyahschool.teachers;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class addClassTestMarks extends Fragment {

    View v;
    Spinner stateSpinner;
    Spinner citiesSpinner;
    private ProgressDialog pDialog;
    private String subject_url = "http://192.168.1.11/school_cms/student-classes/getClasses.json";
    private String subject_Name_url = "http://192.168.1.11/school_cms/ClassTests/getSubjects.json";
    private String Student_Name_url = "http://192.168.1.11/school_cms/students/getStudents.json";
    private String submitclassTest_url= "http://192.168.1.11/school_cms/ClassTests/addClassTest.json";
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

        v = inflater.inflate(R.layout.fragment_add_class_test_marks, container, false);
        stateSpinner = v.findViewById(R.id.classSpinner);
        citiesSpinner = v.findViewById(R.id.subjectSpinner);
        requestQueue = Volley.newRequestQueue(getActivity());

        submitCTest = v.findViewById(R.id.submitCTest);
        enterStudentMarks = v.findViewById(R.id.enterStudentMarks);
        myTestDate = v.findViewById(R.id.myTestDate);
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, subject_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;
                List<String> classNameList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    final String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        JSONObject res = null;
                        String className = null;
                        Integer classId = null;
                        String my_sub_id = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                                res = (JSONObject) jsonArray.get(i);

                                className = res.getString("name");
                                classId= res.getInt("id");

//                                classNameList.add(String.valueOf(className));
//                                classNameList.add(String.valueOf(classId));

                            classSubjectDataList.add(new classSubjectData(classId, className));

                            final classNameAdapter classNameAdapter = new classNameAdapter(getActivity(),R.layout.class_name,R.id.className,classSubjectDataList);
                            stateSpinner.setAdapter(classNameAdapter);

                            final Integer finalClassId = classId;
                            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                            List<String> classNameList = new ArrayList<>();
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
                                        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                            Log.i("My Subject Position-->", showMe.get(position));

                                            Log.i("My classID--->", String.valueOf(subPosition));

                /* -----------------------------------Start submit the class test---------------------------*/
                                    submitCTest.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            Map<String, String> params = new HashMap();
                                            params.put("student_class_id", String.valueOf(subPosition));
                                            params.put("subject_id",showMe.get(position));
                                            params.put("total_marks",totalMarks.getText().toString());
                                            params.put("created_on",myTestDate.getText().toString());
                                            params.put("created_by", String.valueOf(1));

                                            JSONArray ourArray = new JSONArray();
                                            JSONObject ourjsonObject = new JSONObject();
                                            JSONObject mJsonObject = null;

                                            for(int i= 0; i<addCTMarksDataArrayList.size();i++){

                                                submitCTMarksnew.add(myaddCTMarksAdapter.addCTMarksDataArrayList.get(i).getEnterCT().toString());

                                                try {
                                                    mJsonObject  = new JSONObject();

                                                    mJsonObject.put("student_id",StRollNo.get(i));
                                                    mJsonObject.put("marks",submitCTMarksnew.get(i));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                ourArray.put(mJsonObject);
                                            }

                                            params.put("class_test_rows", ourArray.toString());
                                            Log.i("class_test_rows",ourArray.toString());
                                            submitCTMarksnew.clear();
                                            StRollNo.clear();

                                            JSONObject parameters = new JSONObject(params);

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
