package com.ammar.saifiyahschool.teachers.Syllabus;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import static android.support.test.InstrumentationRegistry.getContext;

public class AddSyllabus extends AppCompatActivity implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener  {

    Spinner syllabusClassSpinner,syllabusSubjectSpinner;
    private String type,staff_id,ip,URL,class_id,subject_id;
    private String subject_url;
    private String subject_Name_url;
    private String viewCT_URL;
    private SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    ArrayList<String> mySubName = new ArrayList<>();
    String addSyllabus_URL ;

    List<String> submitAddSyllabus = new ArrayList<String>();

    private Toolbar toolbar ;


    private String add_Chapter_URL ;
    RecyclerView viewProgressRecycler;
    LinearLayoutManager progressLinearLayout;
    ArrayList<AddSyllabusData> addSyllabusDataArrayList = new ArrayList<>();
    AddSyllabusAdapter myAddSyllabusAdapter;

    private Integer empty ;
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    RecyclerView addTopicRecyclerview;
    LinearLayoutManager addTopicLinearLayoutManager;
    ArrayList<addTopicsData> addTopicsDataArrayList = new ArrayList<>();
    addTopicAdapter myAddTopicAdapter;
    Button submitTopicButton,toolBarButton;
    EditText numberOfTopic,syllabusChapterName;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_syllabus);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        subject_url = "http://"+ip+"/school_cms/Schedules/getClasses.json";
        subject_Name_url = "http://"+ip+"/school_cms/Schedules/getSubjects.json";
        add_Chapter_URL = "http://"+ip+"/school_cms/syllabus/viewSyllabus.json";
        addSyllabus_URL ="http://"+ip+"/school_cms/syllabus/addSyllabus.json";

        toolBarButton = findViewById(R.id.add_syllabus_button);
        syllabusClassSpinner = findViewById(R.id.syllabusClassSpinner);
        syllabusSubjectSpinner = findViewById(R.id.syllabusSubjectSpinner);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        myAddSyllabusAdapter = new AddSyllabusAdapter(addSyllabusDataArrayList,getApplicationContext());
        viewProgressRecycler = findViewById(R.id.addSyllabusRecyclerView);
        progressLinearLayout = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        viewProgressRecycler.setLayoutManager(progressLinearLayout);
        viewProgressRecycler.setAdapter(myAddSyllabusAdapter);

        toolbar = findViewById(R.id.add_syllabus_toolbar);
        toolbar.setTitle("Change Syllabus");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(viewProgressRecycler);

        loadClassSubject();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add_syllabus_button:
                final Dialog customDialog;
                LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                View customView = inflater.inflate(R.layout.syllabus_custom_dialog, null);
                // Build the dialog
                customDialog = new Dialog(this, R.style.CustomDialog);
                customDialog.setContentView(customView);

                syllabusChapterName = customDialog.findViewById(R.id.syllabusChapterName);
                submitTopicButton = customDialog.findViewById(R.id.submitTopicButton);
                numberOfTopic =customDialog.findViewById(R.id.numberOfTopic);
                myAddTopicAdapter = new addTopicAdapter(addTopicsDataArrayList,getApplicationContext());
                addTopicRecyclerview = customDialog.findViewById(R.id.addTopicRecyclerview);
                addTopicLinearLayoutManager = new  LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                addTopicRecyclerview.setLayoutManager(addTopicLinearLayoutManager);
                addTopicRecyclerview.setAdapter( myAddTopicAdapter);

                numberOfTopic.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            empty = Integer.valueOf(numberOfTopic.getText().toString());
                            addTopicsData myAddTopicsData;
                            addTopicsDataArrayList.clear();
                            integerArrayList.clear();
                            for (int i = 1; i <= empty; i++) {
                                integerArrayList.add(i);
                                myAddTopicsData = new addTopicsData(String.valueOf(i));
                                addTopicsDataArrayList.add(myAddTopicsData);
                            }
                            myAddSyllabusAdapter.notifyDataSetChanged();
                        }
                        catch (NumberFormatException ands){
                            Toast.makeText(getApplicationContext(),"do something ",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                submitTopicButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(numberOfTopic.getText().toString())) {

                            subject_id = sharedPreferences.getString("subject_id", null);
                            class_id = sharedPreferences.getString("class_id", null);
                            Toast.makeText(getApplicationContext(), String.valueOf(addTopicsDataArrayList.size()), Toast.LENGTH_LONG).show();
//
                            Map<String, String> params = new HashMap();
                            params.put("student_class_id", class_id);
                            params.put("subject_id", subject_id);
                            params.put("created_by", staff_id);

                            JSONArray ourArray = new JSONArray();
                            JSONObject mJsonObject = null;

                            for (int i = 0; i < addTopicsDataArrayList.size(); i++) {

                                Log.i("Show me -->", String.valueOf(addTopicsDataArrayList.get(i)));

                                submitAddSyllabus.add(myAddTopicAdapter.addTopicsDataArrayList.get(i).getSyllabusChapterName().toString());

                                try {
                                    mJsonObject = new JSONObject();

                                    mJsonObject.put("topic", submitAddSyllabus.get(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                ourArray.put(mJsonObject);
                            }

//                        params.put("syllabus_rows", String.valueOf(ourArray));
                            submitAddSyllabus.clear();

                            JSONObject parameters = new JSONObject(params);

                            try {

                                parameters.put("syllabus_rows", ourArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JsonObjectRequest submitMarks = new JsonObjectRequest(
                                    Request.Method.POST, addSyllabus_URL, parameters, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    String CTjsonArray = null;
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.toString());
                                        String success = jsonObject.getString("success");
                                        if (success.equals("true")) {
                                            CTjsonArray = jsonObject.getString("message");
                                            myAddSyllabusAdapter.notifyDataSetChanged();
                                            customDialog.dismiss();

                                            myAddSyllabusAdapter.notifyDataSetChanged();

//                                    addTopicsDataArrayList.notify();
//                                    myAddTopicAdapter.notifyDataSetChanged();

                                            Toast.makeText(getApplicationContext(), CTjsonArray.toString(), Toast.LENGTH_LONG).show();

                                            Log.i("Response Me ----->", String.valueOf(CTjsonArray));

                                        } else {
                                            String msg = jsonObject.getString("message");
                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
                            requestQueue.add(submitMarks);


                        }else {
                            Toast.makeText(AddSyllabus.this,"Please put number for Chapter Text Box",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                customDialog.show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadClassSubject() {

        final List<classSubjectData> classSubjectDataList = new ArrayList<>();
        final ArrayList<String> showMe = new ArrayList<>();

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

//                                classNameList.add(String.valueOf(className));
//                                classNameList.add(String.valueOf(classId));

                            classSubjectDataList.add(new classSubjectData(classId, className));

                            final classNameAdapter classNameAdapter = new classNameAdapter(getApplicationContext(),R.layout.class_name,R.id.className,classSubjectDataList);
                            syllabusClassSpinner.setAdapter(classNameAdapter);

                            syllabusClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final classSubjectData subjectName = classNameAdapter.getItem(position);
//                                    List<Integer> subPosition = Collections.singletonList(subjectName.getClassId());
                                    final int subPosition = subjectName.getClassId();

                                    Map<String, String> params = new HashMap();
                                    params.put("section_id", String.valueOf(subPosition));
                                    params.put("staff_id", staff_id);
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
                                                        syllabusSubjectSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mySubName));


                                                        showMe.add(showId);
                                                    }
//                                                    mySubName = new ArrayList<>();
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

                                    syllabusSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                            Log.i("My Subject Position-->", showMe.get(position));

                                            Log.i("My classID--->", String.valueOf(subPosition));

                                            SharedPreferences.Editor edit = sharedPreferences.edit();
                                            edit.putString("subject_id",showMe.get(position));
                                            edit.putString("class_id",String.valueOf(subPosition));
                                            edit.apply();
//                                            if (integerArrayList.contains(null)){
//                                                Toast.makeText(getApplicationContext(),integerArrayList.size(),Toast.LENGTH_LONG).show();
//                                            }else{
//                                                Toast.makeText(getApplicationContext(),"Enter Text bro",Toast.LENGTH_LONG).show();
//                                            }

                                            Map<String, String> params = new HashMap();
                                            params.put("id", staff_id);
                                            params.put("class_id",String.valueOf(subPosition));
                                            params.put("subject_id",showMe.get(position));

                                            final JSONObject parameters = new JSONObject(params);

                                            JsonObjectRequest myProgressRequest = new JsonObjectRequest(Request.Method.POST, add_Chapter_URL, parameters, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    JSONArray jsonArray = null;
                                                    JSONArray nestedJsonArray = null;
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response.toString());

                                                        String success = jsonObject.getString("success");
                                                        if (success.equals("true")) {
                                                            jsonArray = jsonObject.getJSONArray("response");

                                                            AddSyllabusData myaddSyllabusData = null;
                                                            addTopicsData myAddTopicsData = null;

                                                            for(int i = 0; i < jsonArray.length(); i++)
                                                            {
                                                                JSONObject res = (JSONObject) jsonArray.get(i);

                                                                nestedJsonArray = res.getJSONArray("syllabus_rows");

                                                                for (int j =0;j<nestedJsonArray.length();j++){

                                                                    JSONObject nestedJson = (JSONObject) nestedJsonArray.get(j);

                                                                    myaddSyllabusData = new AddSyllabusData(nestedJson.getString("edited_day"),nestedJson.getString("edited_month"),nestedJson.getString("topic"),nestedJson.getString("id"),nestedJson.getString("status"));
                                                                    addSyllabusDataArrayList.add(myaddSyllabusData);

//                                                                    myAddTopicsData = new addTopicsData(nestedJson.getString("id"),nestedJson.getString("topic"));
//                                                                    addTopicsDataArrayList.add(myAddTopicsData);

                                                                }
                                                                myAddSyllabusAdapter.notifyDataSetChanged();
//                                                                myAddTopicAdapter.notifyDataSetChanged();
                                                            }

                                                        } else {
                                                            String msg = jsonObject.getString("message");
                                                            Toast.makeText(getApplicationContext(),String.valueOf(msg),Toast.LENGTH_LONG).show();

                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

                                                }
                                            }
                                            );
                                            requestQueue.add(myProgressRequest);



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
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, final int position) {


        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you really want to Delete this Chapter ?")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {



        if (viewHolder instanceof AddSyllabusAdapter.AddSyllabusViewHolder) {

            myAddSyllabusAdapter.removeItem(String.valueOf(addSyllabusDataArrayList.get(position).getSyllabusChapterNo()));
            addSyllabusDataArrayList.remove(position);
            myAddSyllabusAdapter.notifyDataSetChanged();
//            myAddSyllabusAdapter.notifyItemRemoved(position);

        }

                    }})
                .setNegativeButton(android.R.string.no, null).show();
                myAddSyllabusAdapter.notifyDataSetChanged();

    }
}
