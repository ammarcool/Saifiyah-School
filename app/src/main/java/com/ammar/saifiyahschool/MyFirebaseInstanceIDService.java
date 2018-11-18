package com.ammar.saifiyahschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    SharedPreferences sharedPreferences;

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d("hello bro", "Refreshed token:---->" + refreshedToken);

        //calling the method store token and passing token
//        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putString("token_id",refreshedToken);
//        edit.apply();

        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
        //we will save the token in sharedpreferences later
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
