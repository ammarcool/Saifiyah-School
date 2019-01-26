package com.ammar.saifiyahschool.teachers.LeaveBalance;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.teachers.AddClassTest.classNameAdapter;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class addLeaveBalance extends Fragment {

    View v,radioButton;
    Spinner leaveTypes;
    ArrayList<leaveTypesData> leaveTypesDataArrayList = new ArrayList<>();
    RequestQueue myrequestQueue;
    private DatePickerDialog.OnDateSetListener mDateSetListener,ToDateListener;
    EditText fromDate,ToDate,leaveBalanceReason;
    RadioGroup radioGroup;
    RadioButton YesHalfDay,NoHalfDay;
    Button submitTeacherLeave;
    String HalfDayYes,HalfDayNo;
    String Types_Leave_URL ;
    String Add_Leave_URL ;

    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;

    public addLeaveBalance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_leave_balance, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        leaveTypes = v.findViewById(R.id.typesOfLeaves);
        fromDate = v.findViewById(R.id.leaveBalanceFromDate);
        fromDate.setInputType(InputType.TYPE_NULL);
        radioGroup = v.findViewById(R.id.leaveBalanceHalfDay);
        YesHalfDay =v.findViewById(R.id.yesHalfDay);
        NoHalfDay = v.findViewById(R.id.noHalfDay);
        leaveBalanceReason = v.findViewById(R.id.leaveBalanceReason);
        submitTeacherLeave =v.findViewById(R.id.submitTeacherLeave);
        ToDate = v.findViewById(R.id.leaveBalanceToDate);
        ToDate.setInputType(InputType.TYPE_NULL);

        myrequestQueue = Volley.newRequestQueue(getContext());

        /*                   Start From Date               */
        fromDate.setOnClickListener(new View.OnClickListener() {
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
                fromDate.setText(date);
            }
        };
        /*                   End From Date                 */
        /* --------------------------------------------------- */
        /*                   Start To Date               */
        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        ToDateListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        ToDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                ToDate.setText(date);
            }
        };

        /*                   End To Date                 */

        submitleaves();
        return v;

    }

    private void submitleaves() {

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        Types_Leave_URL ="http://"+ip+"/school_cms/LeaveTypes/index.json";
        Add_Leave_URL= "http://"+ip+"/school_cms/Leaves/addLeave.json";


        final ArrayList<String> leaveTypesList = new ArrayList<>();
        final ArrayList<String> leaveTypesListID = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Types_Leave_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    final String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        JSONObject res = null;
                        String leaveType = null;
                        String leaveTypeId = null;
                        String typeList = null;
                        String ourID= null;
                        leaveTypesData typesData = null;
                        for (int i=0;i<jsonArray.length();i++){
                            res = (JSONObject) jsonArray.get(i);

                            leaveType = res.getString("type");
                            leaveTypeId= res.getString("id");

                            typesData = new leaveTypesData(leaveTypeId,leaveType);
                            leaveTypesDataArrayList.add(typesData);

                            typeList = typesData.getLeaveType();
                            ourID = typesData.getLeaveTypeID();

                            leaveTypesList.add(typeList);
                            leaveTypesListID.add(ourID);

                            leaveTypes.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, leaveTypesList));
                        }
                            leaveTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                   Log.i("hello bro---->",leaveTypesListID.get(position));

                                /* MY Radio Button  */

                                submitTeacherLeave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (!fromDate.equals("01-01-1970") && !ToDate.equals("01-01-1970")) {


                                            final Map<String, String> params = new HashMap();
                                            params.put("staff_id", staff_id);
                                            params.put("date_from", fromDate.getText().toString());
                                            params.put("date_to", ToDate.getText().toString());
                                            params.put("leave_type_id", leaveTypesListID.get(position));

                                            if (YesHalfDay.isChecked()) {
                                                params.put("half_day", "Yes");
                                            } else {
                                                params.put("half_day", "No");
                                            }
                                            params.put("leave_reason", leaveBalanceReason.getText().toString());
                                            final JSONObject parameters = new JSONObject(params);

                                            JsonObjectRequest addLeaveRequest = new JsonObjectRequest(Request.Method.POST, Add_Leave_URL, parameters, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    String CTjsonArray = null;
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response.toString());
                                                        String success = jsonObject.getString("success");
                                                        if (success.equals("true")) {
                                                            CTjsonArray = jsonObject.getString("message");

                                                            leaveBalanceReason.setText("");
                                                            fromDate.setInputType(InputType.TYPE_NULL);
                                                            ToDate.setInputType(InputType.TYPE_NULL);
                                                            YesHalfDay.setChecked(false);
                                                            NoHalfDay.setChecked(false);

                                                            Toast.makeText(getContext(), CTjsonArray.toString(), Toast.LENGTH_LONG).show();

                                                            Log.i("Response Me ----->", String.valueOf(CTjsonArray));

                                                        } else {
                                                            String msg = jsonObject.getString("message");
                                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                                            Log.i("show my Error====>", msg);
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
                                            myrequestQueue.add(addLeaveRequest);
                                        }else {
                                            Toast.makeText(getContext(),"Please Add From or To Date",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                /* End Radio Button */

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

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
        myrequestQueue.add(jsonObjectRequest);
    }

}
