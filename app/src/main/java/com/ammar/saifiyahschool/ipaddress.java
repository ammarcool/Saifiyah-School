package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ipaddress extends AppCompatActivity {

    private EditText ip;
    private Button b1;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipaddress);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        String user_ip = sharedPreferences.getString("ip",null);
        if (!TextUtils.isEmpty(user_ip))
        {
            Intent it = new Intent(this,MainActivity.class);
            it.putExtra("ip_address",user_ip);
            startActivity(it);
            finish();
        }
        ip = findViewById(R.id.ip_address);
        b1 = findViewById(R.id.submit);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = ip.getText().toString();
                if (!TextUtils.isEmpty(t)) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("ip",t);
                    edit.apply();
                    Intent it = new Intent(ipaddress.this, MainActivity.class);
                    it.putExtra("ip_address", t);
                    startActivity(it);
                    finish();
                }
                else
                    Toast.makeText(ipaddress.this, "hj", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
