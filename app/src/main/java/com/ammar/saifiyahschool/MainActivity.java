package com.ammar.saifiyahschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String ip;
    private EditText username,password;
    private Button login,signup;
    private TextView tv;
    private SharedPreferences sharedPreferences;
    private long backPressedTime;
    private ProgressDialog progressDialog;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        ip = sharedPreferences.getString("ip",null);

        if(TextUtils.isEmpty(ip))
        {
            Intent intent = new Intent(MainActivity.this, ipaddress.class);
            startActivity(intent);
            finish();
        }


        String uname = sharedPreferences.getString("username",null);
        String pwd = sharedPreferences.getString("password",null);


        if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pwd)) {

            String URL = "http://" + ip + "/school_cms/logins/loginApi.json";

            Map<String, String> params = new HashMap();
            params.put("username", uname);
            params.put("password", pwd);
            JSONObject parameters = new JSONObject(params);

            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    parameters,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            JSONArray getToken;
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String success = jsonObject.getString("success");
                                if (success.equals("true")) {
                                    JSONObject user = jsonObject.getJSONObject("response");

                                    getToken = user.getJSONArray("tokens");

                                    for (int i = 0; i < getToken.length(); i++) {
                                        JSONObject oldToken = (JSONObject) getToken.get(i);
                                        String myToken = oldToken.getString("token_no");
                                        Log.i("DB Token--->",myToken);
                                        Log.i("Shared Token---->",SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken());
                                        if (myToken.equals( SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken())) {
//                                            progressDialog.dismiss();
                                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            progressDialog.dismiss();
                                            SharedPreferences.Editor edit = sharedPreferences.edit();
                                            edit.remove("username");
                                            edit.remove("password");
                                            edit.remove("id");
                                            edit.remove("type");
                                            edit.remove("student_class_id");
                                            edit.apply();
//                                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
//                                        startActivity(it);
//                                        finish();
                                        }
                                    }

                                } else {
                                    String msg = jsonObject.getString("message");
                                    tv.setText(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("API Response", error.toString());
                        }
                    }
            );
            requestQueue.add(objectRequest);
        }else {
            progressDialog.dismiss();
//
        }

        username = findViewById(R.id.uname);
        password = findViewById(R.id.pwd);
        login = findViewById(R.id.login);
        tv = findViewById(R.id.error);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Required");
                }
                else {
                    if (TextUtils.isEmpty(password.getText().toString())) {
                        password.setError("Required");
                    } else {


                        String URL = "http://" + ip + "/school_cms/logins/loginApi.json";

                        Map<String, String> params = new HashMap();
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());
                        JSONObject parameters = new JSONObject(params);



                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                URL,
                                parameters,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.toString());
                                            String success = jsonObject.getString("success");
                                            if (success.equals("true")) {
                                                JSONObject user = jsonObject.getJSONObject("response");
                                                String t = user.getString("type");
                                                String login_id = user.getString("id");
                                                String id = null;
                                                if (t.equals("student"))
                                                    id = user.getString("student_id");
                                                else if (t.equals("staff"))
                                                    id = user.getString("staff_id");
                                                else
                                                    id = "0";

                                                SharedPreferences.Editor edit = sharedPreferences.edit();

                                                edit.putString("username",username.getText().toString());
                                                edit.putString("password",password.getText().toString());
                                                edit.putString("login_id",login_id);
                                                edit.putString("id",id);
                                                edit.putString("type",t);
                                                edit.apply();

                                                /****** Start Token and Login ID ********************/

                                                String Token_URL = "http://"+ip+"/school_cms/tokens/add.json";

                                                Map<String, String> param = new HashMap();
                                                param.put("login_id", sharedPreferences.getString("login_id",null));
                                                param.put("token_no", SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken());

                                                JSONObject Myparams = new JSONObject(param);


                                                JsonObjectRequest tokenObjectRequest = new JsonObjectRequest(
                                                        Request.Method.POST,
                                                        Token_URL,
                                                        Myparams,
                                                        new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response.toString());
                                                                    String success = jsonObject.getString("success");
                                                                    if (success.equals("true")) {
                                                                        JSONObject user = jsonObject.getJSONObject("response");
                                                                        Toast.makeText(getApplicationContext(),user.toString(),Toast.LENGTH_LONG).show();
                                                                    } else {
                                                                        String msg = jsonObject.getString("message");
                                                                        tv.setText(msg);
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Log.e("API Response", error.toString());
                                                            }
                                                        }
                                                );
                                                requestQueue.add(tokenObjectRequest);



                                                /****** End Token and Login ID ********************/


                                                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                String msg = jsonObject.getString("message");
                                                tv.setText(msg);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("API Response", error.toString());
                                    }
                                }
                        );
                        requestQueue.add(objectRequest);


                    }
                }
            }
        });
    }

    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        backPressedTime = System.currentTimeMillis();
    }
}