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
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            URL = "http://" + ip + "/school_cms/fees/feesApi.json";

            Map<String, String> params = new HashMap();
            params.put("student_id", id);
            params.put("student_class_id", class_id);
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
                                    feesDue.setText(jsonArray.getString("due"));
                                    feeTransaction = jsonArray.getJSONArray("fee_rows");
                                    FeesTransactionData feesTransactionData;

                                    if (feeTransaction == null){
                                        Toast.makeText(Fees.this,"No Transaction has been found",Toast.LENGTH_LONG).show();
                                        Log.i("Our Status===>","NO Data Found");
                                    }else{
                                        for (int i = 0; i<feeTransaction.length();i++){
                                            res = (JSONObject) feeTransaction.get(i);

                                        feesTransactionData = new FeesTransactionData("1st Tearm Fees",res.getString("date"),Integer.parseInt(res.getString("credit")),Integer.parseInt(res.getString("credit")));
                                        feesTransactionDataArrayList.add(feesTransactionData);
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
            objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(objectRequest);

        }
        else
//            tv.setText("Uh-oh Something Went Wrong");
        Log.i("Error","class id->"+class_id+" ID->"+id+" IP->"+ip);

    }


}
