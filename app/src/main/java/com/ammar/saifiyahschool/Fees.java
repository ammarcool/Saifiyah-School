package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Fees extends AppCompatActivity {

    private ArrayList<FeesTransactionData> feesTransactionDataArrayList = new ArrayList<>();
    private RecyclerView feesRecyclerview;
    private FeesTransactionAdapter feesTransactionAdapter;
    private String type,id,ip,URL,class_id,total_fees;
    private TextView tv;
    private SharedPreferences sharedPreferences;

    TextView totalFees,totalYearRupeesIcon,feesDue;
    Typeface totalfeesfont;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your Fee's");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        totalFees =(TextView)findViewById(R.id.totalAmount);
        feesRecyclerview =(RecyclerView)findViewById(R.id.fees_recyclerView);
//        totalYearRupeesIcon =(TextView)findViewById(R.id.totalYearRupeesIcon);
        feesDue = (TextView)findViewById(R.id.dueAmount);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        total_fees = sharedPreferences.getString("total_fees",null);

        //call your Api our here
        totalFees.setText("\u20B9 "+total_fees);

        //Font
        totalfeesfont = Typeface.createFromAsset(getAssets(), "fonts/cabinsketch.otf");
        totalFees.setTypeface(totalfeesfont);
//        totalYearRupeesIcon.setTypeface(totalfeesfont);

        feesTransactionAdapter = new FeesTransactionAdapter(feesTransactionDataArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        feesRecyclerview.setLayoutManager(mLayoutManager);
        feesRecyclerview.setAdapter(feesTransactionAdapter);

        addFeesTransactionData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void addFeesTransactionData() {

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        class_id = sharedPreferences.getString("student_class_id",null);
        id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        if(!TextUtils.isEmpty(class_id) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(ip))
        {
            URL = "http://" + ip + "/school_cms/Students/getFees.json";

            Map<String, String> params = new HashMap();
            params.put("id", id);
//            params.put("student_class_id", class_id);
            JSONObject parameters = new JSONObject(params);

            RequestQueue requestQueue = Volley.newRequestQueue(Fees.this);

            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    parameters,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONObject jsonArray = null;
                            JSONArray feeTransaction = null;
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String success = jsonObject.getString("success");
                                if (success.equals("true")) {
                                    jsonArray = jsonObject.getJSONObject("response");
                                    JSONObject res = null;
                                    feesDue.setText("nothing");
                                    feeTransaction = jsonArray.getJSONArray("fee_terms");
                                    FeesTransactionData feesTransactionData;

                                    if (feeTransaction == null){
                                        Toast.makeText(Fees.this,"No Transaction has been found",Toast.LENGTH_LONG).show();
                                        Log.i("Our Status===>","NO Data Found");
                                    }else{
                                        for (int i = 0; i<feeTransaction.length();i++){
                                            res = (JSONObject) feeTransaction.get(i);

                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
                                            Date start_date,end_date;
                                            try {
                                                start_date = df.parse(res.getString("start_date"));
                                                end_date = df.parse(res.getString("end_date"));
                                                System.out.println("date:"+start_date); //prints date in current locale
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                                System.out.println(sdf.format(start_date));
                                                Log.i("Date-->", sdf.format(start_date));//prints date in the format sdf

                                                feesTransactionData = new FeesTransactionData(res.getString("name"),res.getString("start_date"),res.getString("end_date"),res.getString("term_amount"),res.getString("submit_on"));
                                                feesTransactionDataArrayList.add(feesTransactionData);

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        feesTransactionAdapter.notifyDataSetChanged();
                                    }


//                                    for(int i = 0; i < jsonArray.length(); i++)
//                                    {
//                                        res = (JSONObject) jsonArray.get(i);

//                                        feesTransactionData = new FeesTransactionData("1st Tearm Fees",res.getString("date"),Integer.parseInt(res.getString("credit")),Integer.parseInt(res.getString("credit")));
//                                        feesTransactionDataArrayList.add(feesTransactionData);
//                                    }

//                                    feesTransactionAdapter.notifyDataSetChanged();
                                } else {
                                    String msg = jsonObject.getString("message");
//                                    tv.setText(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                            } else if (error instanceof ServerError) {
                            } else if (error instanceof AuthFailureError) {
                            } else if (error instanceof ParseError) {
                            } else if (error instanceof NoConnectionError) {
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(Fees.this, "Oops. Timeout error!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            objectRequest.setRetryPolicy(policy);

            objectRequest.setRetryPolicy(new RetryPolicy() {
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

            requestQueue.add(objectRequest);

        }
        else
//            tv.setText("Uh-oh Something Went Wrong");
        Log.i("Error","class id->"+class_id+" ID->"+id+" IP->"+ip);

    }


}
