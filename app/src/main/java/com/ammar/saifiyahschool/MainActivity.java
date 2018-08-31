package com.ammar.saifiyahschool;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pwd))
        {
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
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

                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

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
                                            String msg = jsonObject.getString("message");
                                            Log.e("error",success);
                                            if (success.equals("true")) {
                                                JSONObject user = jsonObject.getJSONObject("response");
                                                String t = user.getString("type");
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
                                                edit.putString("id",id);
                                                edit.putString("type",t);
                                                edit.apply();

                                                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
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