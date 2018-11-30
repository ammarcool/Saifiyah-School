package com.ammar.saifiyahschool.teachers.LeaveBalance;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.CYAN;
import static android.graphics.Color.RED;

/**
 * A simple {@link Fragment} subclass.
 */
public class viewLeaveBalance extends Fragment {

    View v;
    private String leave_balance_URL ;
    RecyclerView viewTeacherLeaves;
    RequestQueue teacherLeaveRequestQueue;
    LinearLayoutManager teacherLeavesLinearLayoutManager;
    ArrayList<viewLeaveBalanceData> viewLeaveBalanceDataArrayList = new ArrayList<>();
    viewLeaveBalanceAdapter viewLeaveBalanceAdapterObj;

    DecoView myDecoView;
    int series1Index;
    BounceInterpolator bounceInterpolator;
    Interpolator interpolator = new BounceInterpolator();

    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;

    public viewLeaveBalance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_view_leave_balance, container, false);



        viewLeaveBalanceAdapterObj = new viewLeaveBalanceAdapter(viewLeaveBalanceDataArrayList,getContext());
        teacherLeaveRequestQueue = Volley.newRequestQueue(getActivity());
        viewTeacherLeaves = v.findViewById(R.id.viewTeacherLeaves);
        teacherLeavesLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        viewTeacherLeaves.setLayoutManager(teacherLeavesLinearLayoutManager);
        viewTeacherLeaves.setAdapter(viewLeaveBalanceAdapterObj);
        myDecoView = v.findViewById(R.id.mydynamicArcView);

        dashboardLeaveBalance();

        viewLB();
        return v;
    }

    /*-----------------------------Start Pie Chart ------------------------------------------ */

    private void dashboardLeaveBalance() {
        myDecoView.configureAngles(360, 0);
        myDecoView.setHorizGravity(DecoView.HorizGravity.GRAVITY_HORIZONTAL_FILL);
        myDecoView.setVertGravity(DecoView.VertGravity.GRAVITY_VERTICAL_BOTTOM);

        //2. Create background track
        myDecoView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(35f)
                .build()
        );

        //3. Create data Series track
        final SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(35f)
                .setInitialVisibility(false)
                .setCapRounded(true)

                .setShowPointWhenEmpty(true)
                .build();

        series1Index = myDecoView.addSeries(seriesItem1);

        //4. Animation Data Series
        myDecoView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(1000)
                .setDuration(2000)
                .build()
        );

        addAnimation(myDecoView, series1Index, 100f, 3500, bounceInterpolator, CYAN, true);
    }

    private void addAnimation(final DecoView arcView, int series, float moveTo, int delay, BounceInterpolator bounceInterpolator,
        final int color, final boolean restart) {


            arcView.addEvent(new DecoEvent.Builder(moveTo)
                    .setIndex(series)
                    .setDelay(delay)
                    .setDuration(4000)
                    .setInterpolator(interpolator)
                    .setColor(color)
                    .build());
        }

    /*-----------------------------End Pie Chart ------------------------------------------ */

    private void viewLB() {

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);
        leave_balance_URL  ="http://"+ip+"/school_cms/Leaves/viewLeave.json";

        Map<String, String> params = new HashMap();
        params.put("id", staff_id);
        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest leaveBalanceRequest = new JsonObjectRequest(Request.Method.POST, leave_balance_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        jsonArray = jsonObject.getJSONArray("response");
                        viewLeaveBalanceData newviewLeaveBalanceData = null;

                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject res = (JSONObject) jsonArray.get(i);

                            newviewLeaveBalanceData = new viewLeaveBalanceData(res.getString("day"),res.getString("month"),res.getString("from"),res.getString("to"),res.getString("duretion"),res.getString("type"),res.getString("status"),res.getString("half_day"));
                            viewLeaveBalanceDataArrayList.add(newviewLeaveBalanceData);
                        }

                        viewLeaveBalanceAdapterObj.notifyDataSetChanged();
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

            }
        }
        );
        teacherLeaveRequestQueue.add(leaveBalanceRequest);
    }

}
