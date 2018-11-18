package com.ammar.saifiyahschool.teachers.AddClassTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewClassTestAdapter  extends RecyclerView.Adapter<viewClassTestAdapter.CTViewHolder> {


    ArrayList<viewCTData> ctDataArrayList;
    Context context;
    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;
    String deleteCT_URL ;

    RequestQueue requestQueue;

    viewClassTestAdapter(Context context, ArrayList<viewCTData> ctDataArrayList) {
        this.ctDataArrayList = ctDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classtest_view_recyclerview, parent, false);
        requestQueue = Volley.newRequestQueue(context);

        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        return new CTViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CTViewHolder holder, int position) {

        holder.ctDay.setText(Integer.toString(ctDataArrayList.get(position).getCtDay()));
        holder.ctMonth.setText(ctDataArrayList.get(position).getCtMonth());
        holder.CTSubject.setText(ctDataArrayList.get(position).getCtSubject());
        holder.totalCTMarks.setText(Integer.toString(ctDataArrayList.get(position).getCtMarks()));
        holder.CTClass.setText(ctDataArrayList.get(position).getCtClass());
        holder.deleteCT.findViewById(R.id.deleteCT);
//        holder.deleteCT.setText(ctDataArrayList.get(position).getDeletePosition());

    }

    @Override
    public int getItemCount() {
        return ctDataArrayList.size();
    }

    public void deleteFromServer(int id) {



        deleteCT_URL = "http://"+ip+"/school_cms/ClassTests/deleteClassTest.json";

        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(id));
        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest deleteJSON = new JsonObjectRequest(Request.Method.POST, deleteCT_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String deleteMsg = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    String success = jsonObject.getString("success");

                    if (success.equals("true")) {
                        deleteMsg = jsonObject.getString("message");


                        Toast.makeText(context, deleteMsg, Toast.LENGTH_LONG).show();
                        ctDataArrayList.clear();

                    } else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );


        requestQueue.add(deleteJSON);
    }


    class CTViewHolder extends RecyclerView.ViewHolder {

        TextView ctDay;
        TextView ctMonth;
        TextView CTSubject;
        TextView totalCTMarks;
        TextView CTClass;
        Button deleteCT;

        @SuppressLint("ClickableViewAccessibility")
        public CTViewHolder(@NonNull View itemView) {
            super(itemView);

            ctDay = itemView.findViewById(R.id.ctDay);
            ctMonth = itemView.findViewById(R.id.addSyllabusMonth);
            CTSubject = itemView.findViewById(R.id.CTSubject);
            totalCTMarks = itemView.findViewById(R.id.totalCTMarks);
            CTClass = itemView.findViewById(R.id.CTClass);
            deleteCT = itemView.findViewById(R.id.deleteCT);

            deleteCT.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                            v.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            v.getBackground().clearColorFilter();
                            v.invalidate();
                            break;
                        }
                    }
                    return false;
                }
            });

            deleteCT.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteFromServer(Integer.parseInt(ctDataArrayList.get(getAdapterPosition()).getDeletePosition()));
                    ctDataArrayList.remove(getAdapterPosition()).getDeletePosition();
                    notifyItemRemoved(getAdapterPosition());
                Toast.makeText(context,"Your Test has been deleted",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
