package com.ammar.saifiyahschool.teachers.Progress;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
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
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class viewProgress extends Fragment {

    View v;

    private String progress_URL ;
    RecyclerView viewProgressRecycler;
    RequestQueue progressRequest;
    LinearLayoutManager progressLinearLayout;
    ArrayList<viewProgressData> viewProgressDataArrayList = new ArrayList<>();
    viewProgressAdapter myviewProgressAdapter;

    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;



    public viewProgress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_view_progress, container, false);

        myviewProgressAdapter = new viewProgressAdapter(viewProgressDataArrayList,getContext());
        progressRequest = Volley.newRequestQueue(getActivity());
        viewProgressRecycler = v.findViewById(R.id.viewProgressRecycler);
        progressLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        viewProgressRecycler.setLayoutManager(progressLinearLayout);
        viewProgressRecycler.setAdapter(myviewProgressAdapter);
        viewProgressMethod();

        return v;
    }

    private void viewProgressMethod() {

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        progress_URL  ="http://"+ip+"/school_cms/Progresses/viewProgress.json";

        Map<String, String> params = new HashMap();
        params.put("id", staff_id);
        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest myProgressRequest = new JsonObjectRequest(Request.Method.POST, progress_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        viewProgressData newViewProgressData = null;

                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject res = (JSONObject) jsonArray.get(i);

                            newViewProgressData = new viewProgressData(res.getString("class"),res.getString("student"),res.getString("title"),res.getString("is_reward"),res.getString("month"),res.getString("day"));
                            viewProgressDataArrayList.add(newViewProgressData);
                        }
                        myviewProgressAdapter.notifyDataSetChanged();
                    } else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getContext(),String.valueOf(msg),Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),String.valueOf(e),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }
        );

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myProgressRequest.setRetryPolicy(policy);

        myProgressRequest.setRetryPolicy(new RetryPolicy() {
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
        progressRequest.add(myProgressRequest);
    }

//    public static void expand(final View v) {
//        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        final int targetHeight = v.getMeasuredHeight();
//
//        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        v.getLayoutParams().height = 1;
//        v.setVisibility(View.VISIBLE);
//
//        ValueAnimator va = ValueAnimator.ofInt(1, targetHeight);
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
//                v.requestLayout();
//            }
//        });
//        va.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            }
//
//            @Override public void onAnimationStart(Animator animation) {}
//            @Override public void onAnimationCancel(Animator animation) {}
//            @Override public void onAnimationRepeat(Animator animation) {}
//        });
//        va.setDuration(300);
//        va.setInterpolator(new OvershootInterpolator());
//        va.start();
//    }
//
//    public static void collapse(final View v) {
//        final int initialHeight = v.getMeasuredHeight();
//
//        ValueAnimator va = ValueAnimator.ofInt(initialHeight, 0);
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
//                v.requestLayout();
//            }
//        });
//        va.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                v.setVisibility(View.GONE);
//            }
//
//            @Override public void onAnimationStart(Animator animation) {}
//            @Override public void onAnimationCancel(Animator animation) {}
//            @Override public void onAnimationRepeat(Animator animation) {}
//        });
//        va.setDuration(300);
//        va.setInterpolator(new DecelerateInterpolator());
//        va.start();
//    }

}
