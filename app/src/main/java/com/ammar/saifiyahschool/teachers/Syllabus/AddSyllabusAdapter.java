package com.ammar.saifiyahschool.teachers.Syllabus;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
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

public class AddSyllabusAdapter extends RecyclerView.Adapter<AddSyllabusAdapter.AddSyllabusViewHolder> {

    ArrayList<AddSyllabusData> addSyllabusDataArrayList;
    Context context;
    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;
    String deleteCT_URL ;
    String change_syllabus_Name;

    RequestQueue requestQueue;

    public AddSyllabusAdapter(ArrayList<AddSyllabusData> addSyllabusDataArrayList, Context context) {
        this.addSyllabusDataArrayList = addSyllabusDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddSyllabusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_syllabus_recyclerview, parent, false);

        requestQueue = Volley.newRequestQueue(context);

        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);

        return new AddSyllabusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddSyllabusViewHolder holder, int position) {
        holder.addSyllabusDay.setText(addSyllabusDataArrayList.get(position).getAddSyllabusDay());
        holder.addSyllabusMonth.setText(addSyllabusDataArrayList.get(position).getAddSyllabusMonth());
        holder.syllabusTopicName.setText(addSyllabusDataArrayList.get(position).getSyllabusTopicName());
        holder.syllabusChapterNo.setText(addSyllabusDataArrayList.get(position).getSyllabusChapterNo());
        holder.changeChapterName.setText(addSyllabusDataArrayList.get(position).getChangeSyllabusName());

        if (addSyllabusDataArrayList.get(position).getDoneUndoUnit() == "true") {
            holder.doneUndoUnit.setChecked(true);
        } else {
            holder.doneUndoUnit.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return addSyllabusDataArrayList.size();
    }


    public void removeItem(final String position) {

//        addSyllabusDataArrayList.remove(position);
//        notifyItemRemoved(Integer.parseInt(position));
//
//        notifyItemRangeChanged(Integer.parseInt(position),addSyllabusDataArrayList.size());
//        notifyItemChanged(Integer.parseInt(position));
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
     String   delete_Syllabus_URL = "http://"+ip+"/school_cms/SyllabusRows/delete.json";

        Map<String, String> params = new HashMap();
        params.put("id", position);
        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest deleteSyllabusJson = new JsonObjectRequest(Request.Method.POST, delete_Syllabus_URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String deleteMsg = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    String success = jsonObject.getString("success");

                    if (success.equals("true")) {
                        deleteMsg = jsonObject.getString("message");

                        Toast.makeText(context, deleteMsg, Toast.LENGTH_LONG).show();

//                        addSyllabusDataArrayList.clear();

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
        requestQueue.add(deleteSyllabusJson);



    }

    public void updateFromServer(int id,int status) {

        deleteCT_URL = "http://"+ip+"/school_cms/SyllabusRows/changeStatus.json";

        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(id));
        params.put("status",String.valueOf(status));
//        params.put("topic",addSyllabusDataArrayList.get(id).getSyllabusTopicName());
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
//                        Toast.makeText(context, deleteMsg, Toast.LENGTH_LONG).show();
//                        addSyllabusDataArrayList.clear();

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

    public void updateChapterName(int id) {

        deleteCT_URL = "http://"+ip+"/school_cms/SyllabusRows/changeStatus.json";
        change_syllabus_Name = sharedPreferences.getString("change_Syllabus",null);

        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(id));
        params.put("topic",change_syllabus_Name);
        Log.i("Testing Params",params.toString());
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
//                        Toast.makeText(context, deleteMsg, Toast.LENGTH_LONG).show();
//                        addSyllabusDataArrayList.clear();

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

    class AddSyllabusViewHolder extends RecyclerView.ViewHolder {

        TextView addSyllabusDay;
        TextView addSyllabusMonth;
        TextView syllabusTopicName;
        TextView syllabusChapterNo;
        Switch doneUndoUnit;
        RelativeLayout view_background;
        ConstraintLayout view_foreground;
        EditText changeChapterName;

        public AddSyllabusViewHolder(@NonNull View itemView) {
            super(itemView);

            addSyllabusDay = itemView.findViewById(R.id.addSyllabusDay);
            addSyllabusMonth = itemView.findViewById(R.id.addSyllabusMonth);
            syllabusTopicName = itemView.findViewById(R.id.syllabusTopicName);
            syllabusChapterNo = itemView.findViewById(R.id.syllabusChapterNo);
            doneUndoUnit = itemView.findViewById(R.id.doneUndoUnit);
            view_background = itemView.findViewById(R.id.view_background);
            view_foreground = itemView.findViewById(R.id.view_foreground);
            changeChapterName = itemView.findViewById(R.id.changeChapterName);


            syllabusTopicName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    syllabusTopicName.setVisibility(View.GONE);
                    doneUndoUnit.setVisibility(View.GONE);
                    changeChapterName.setVisibility(View.VISIBLE);

                    changeChapterName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("change_Syllabus",changeChapterName.getText().toString());
                            edit.apply();

                            updateChapterName(Integer.parseInt(addSyllabusDataArrayList.get(getAdapterPosition()).getSyllabusChapterNo()));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
//                            syllabusTopicName.setVisibility(View.VISIBLE);
//                            doneUndoUnit.setVisibility(View.VISIBLE);
//                            changeChapterName.setVisibility(View.INVISIBLE);

                        }
                    });

                    return true;
                }
            });

            view_foreground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    changeChapterName.setVisibility(syllabusTopicName.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                    syllabusTopicName.setVisibility(syllabusTopicName.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                    doneUndoUnit.setVisibility(doneUndoUnit.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);

                }
            });

//            syllabusTopicName.setVisibility(View.VISIBLE);
//            doneUndoUnit.setVisibility(View.VISIBLE);
//            changeChapterName.setVisibility(View.INVISIBLE);


            doneUndoUnit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked){
                        updateFromServer(Integer.parseInt(addSyllabusDataArrayList.get(getAdapterPosition()).getSyllabusChapterNo()),1);
                        Toast.makeText(context,"This Chapter has been Completed",Toast.LENGTH_LONG).show();
                    }else {
                        updateFromServer(Integer.parseInt(addSyllabusDataArrayList.get(getAdapterPosition()).getSyllabusChapterNo()),0);
                        Toast.makeText(context,"This Chapter is Still Pending",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}
