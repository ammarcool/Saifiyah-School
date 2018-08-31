package com.ammar.saifiyahschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText username,password ,tv;
    Button submit;
    String url = "http://192.168.43.52/school_cms/logins/loginApi.json";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =(EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        submit = (Button)findViewById(R.id.submit);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void submitData(View view) {

        Map<String, String> params = new HashMap();
        params.put("username", username.getText().toString());
        params.put("password", password.getText().toString());
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String msg = jsonObject.getString("success");
                    if(msg.equals("true"))
                    {
                        JSONObject user = jsonObject.getJSONObject("response");
                        String t = user.getString("type");
                        String id = null;
                        if(t.equals("student"))
                            id = user.getString("student_id");
                        else
                        if(t.equals("staff"))
                            id = user.getString("staff_id");
                        else
                            id = "0";


                        Intent intent = new Intent(MainActivity.this,Dashboard.class);
                        intent.putExtra("type",t);
                        intent.putExtra("id",id);

                        startActivity(intent);
                    }
                    else
                    {
                        tv.setText(msg);
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
        });
//        {
//
//            @Override
//        protected Map<String, String> getParams() {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("username", username.getText().toString());
//            params.put("password", password.getText().toString());
//            return params;
//        }
//    };
        requestQueue.add(jsonObjectRequest);
    }
}
