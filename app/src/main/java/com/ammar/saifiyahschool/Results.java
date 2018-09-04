package com.ammar.saifiyahschool;


import android.os.Bundle;
import android.app.Activity;

public class Results extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TableMainLayout(this));
    }
}