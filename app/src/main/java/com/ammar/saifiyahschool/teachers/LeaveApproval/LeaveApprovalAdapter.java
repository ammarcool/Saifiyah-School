package com.ammar.saifiyahschool.teachers.LeaveApproval;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class LeaveApprovalAdapter extends RecyclerView.Adapter<LeaveApprovalAdapter.LeaveApprovalViewHolder> {

    ArrayList<LeaveApprovalData> leaveApprovalDataArrayList;
    Context context;
    int mExpandedPosition = -1;

    private SharedPreferences sharedPreferences;
    String staff_id,type,ip;
    String deleteCT_URL ;

    RequestQueue requestQueue;

    public LeaveApprovalAdapter(ArrayList<LeaveApprovalData> leaveApprovalDataArrayList, Context context) {
        this.leaveApprovalDataArrayList = leaveApprovalDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_approval_recyclerview, parent, false);

        requestQueue = Volley.newRequestQueue(context);

        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        type = sharedPreferences.getString("type",null);
        staff_id = sharedPreferences.getString("id",null);
        ip = sharedPreferences.getString("ip",null);


        return new LeaveApprovalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveApprovalViewHolder holder, final int position) {

        holder.leaveApproveDate.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveDate());
        holder.leaveApproveMonth.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveMonth());
        holder.leaveApproveTeacherName.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveTeacherName());
        holder.leaveApproveTypeOfLeave.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveTypeOfLeave());
        holder.leaveApproveFromDate.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveFromDate());
        holder.leaveApproveToDate.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveToDate());
        holder.leaveApproveReason.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveReason());
        holder.leaveApproveHalfDay.setText(leaveApprovalDataArrayList.get(position).getLeaveApproveHalfDay());
        holder.leaveApproveDropDownIcon.setImageResource(R.drawable.drop_down);


//        if (leaveApprovalDataArrayList.get(position).getLeaveStatusAfterAction() == "0"){
//            holder.leaveStatusAfterAction.setVisibility(View.VISIBLE);
//            holder.leaveApproveBtn.setVisibility(View.GONE);
//            holder.leaveApproveBtnGap.setVisibility(View.GONE);
//            holder.leaveRejectBtn.setVisibility(View.GONE);
//            holder.leaveStatusAfterAction.setText("You already Accept this Leave");
//
//        }else if (leaveApprovalDataArrayList.get(position).getLeaveStatusAfterAction() == "1"){
//            holder.leaveStatusAfterAction.setVisibility(View.VISIBLE);
//            holder.leaveApproveBtn.setVisibility(View.GONE);
//            holder.leaveApproveBtnGap.setVisibility(View.GONE);
//            holder.leaveRejectBtn.setVisibility(View.GONE);
//            holder.leaveStatusAfterAction.setText("You already Reject this Leave");
//        }
//        else {
//            holder.leaveStatusAfterAction.setVisibility(View.GONE);
//            holder.leaveApproveBtn.setVisibility(View.VISIBLE);
//            holder.leaveApproveBtnGap.setVisibility(View.VISIBLE);
//            holder.leaveRejectBtn.setVisibility(View.VISIBLE);
//        }

        final boolean isExpanded = position==mExpandedPosition;
        holder.leaveApproveHalfDayLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.leaveApproveDateLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.leaveApproveReasonLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.leaveApproveHorizontalLine.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.leaveApproveButtonLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                holder.leaveApproveDropDownIcon.setImageResource(R.drawable.collapse_less);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return leaveApprovalDataArrayList.size();
    }

    public void updateFromServer(int id,int status) {

//        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
//        ip = sharedPreferences.getString("ip",null);

        deleteCT_URL = "http://"+ip+"/school_cms/Leaves/leaveApprove.json";

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

     class LeaveApprovalViewHolder extends RecyclerView.ViewHolder{

        TextView leaveApproveDate;
        TextView leaveApproveMonth;
        TextView leaveApproveTeacherName;
        TextView leaveApproveTypeOfLeave;
        TextView leaveApproveFromDate;
        TextView leaveApproveToDate;
        TextView leaveApproveReason;
        TextView leaveApproveHalfDay;

        TextView leaveStatusAfterAction;
        Button leaveApproveBtn;
        TextView leaveApproveBtnGap;
        Button leaveRejectBtn;

        ImageView leaveApproveDropDownIcon;
        LinearLayout leaveApproveDateLayout;
        LinearLayout leaveApproveReasonLayout;
        RelativeLayout leaveApproveHorizontalLine;
        LinearLayout leaveApproveButtonLayout;
        LinearLayout leaveApproveHalfDayLayout;

         public LeaveApprovalViewHolder(@NonNull View itemView) {
             super(itemView);

             leaveApproveDate = itemView.findViewById(R.id.leaveApproveDate);
             leaveApproveMonth = itemView.findViewById(R.id.leaveApproveMonth);
             leaveApproveTeacherName = itemView.findViewById(R.id.leaveApproveTeacherName);
             leaveApproveTypeOfLeave = itemView.findViewById(R.id.leaveApproveTypeOfLeave);
             leaveApproveFromDate = itemView.findViewById(R.id.leaveApproveFromDate);
             leaveApproveToDate = itemView.findViewById(R.id.leaveApproveToDate);
             leaveApproveReason = itemView.findViewById(R.id.leaveApproveReason);
             leaveApproveHalfDay = itemView.findViewById(R.id.leaveApproveHalfDay);

             leaveStatusAfterAction = itemView.findViewById(R.id.leaveStatusAfterAction);
             leaveApproveBtn = itemView.findViewById(R.id.leaveApproveBtn);
             leaveApproveBtnGap = itemView.findViewById(R.id.leaveApproveBtnGap);
             leaveRejectBtn = itemView.findViewById(R.id.leaveRejectBtn);

             leaveApproveDropDownIcon = itemView.findViewById(R.id.leaveApproveDropDownIcon);
             leaveApproveDateLayout = itemView.findViewById(R.id.leaveApproveDateLayout);
             leaveApproveReasonLayout = itemView.findViewById(R.id.leaveApproveReasonLayout);
             leaveApproveHorizontalLine = itemView.findViewById(R.id.leaveApproveHorizontalLine);
             leaveApproveButtonLayout = itemView.findViewById(R.id.leaveApproveButtonLayout);
             leaveApproveHalfDayLayout = itemView.findViewById(R.id.leaveApproveHalfDayLayout);


             leaveApproveBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     new AlertDialog.Builder(context)
                             .setTitle("Leave Approval")
                             .setMessage("Do you really want to Approve this Leave ?")
                             .setIcon(android.R.drawable.ic_menu_info_details)
                             .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                 public void onClick(DialogInterface dialog, int whichButton) {

//                                     leaveApprovalDataArrayList.get(getAdapterPosition()).setLeaveStatusAfterAction("0");
                                   updateFromServer(Integer.parseInt(leaveApprovalDataArrayList.get(getAdapterPosition()).getLeaveApprovalID()),1);
                                     leaveStatusAfterAction.setVisibility(View.VISIBLE);
                                     leaveStatusAfterAction.setText("Leave has been Approved");
                                     leaveApproveBtnGap.setVisibility(View.GONE);
                                     leaveApproveBtn.setVisibility(View.GONE);
                                     leaveRejectBtn.setVisibility(View.GONE);

                                 }})
                             .setNegativeButton(android.R.string.no, null).show();

                 }
             });

             leaveRejectBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     new AlertDialog.Builder(context)
                             .setTitle("Leave Approval")
                             .setMessage("Do you really want to Reject This Leave ?")
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                 public void onClick(DialogInterface dialog, int whichButton) {

//                                     leaveApprovalDataArrayList.get(getAdapterPosition()).setLeaveStatusAfterAction("1");
                                   updateFromServer(Integer.parseInt(leaveApprovalDataArrayList.get(getAdapterPosition()).getLeaveApprovalID()),2);
                                     leaveStatusAfterAction.setVisibility(View.VISIBLE);
                                     leaveStatusAfterAction.setText("Leave has been Rejected");
                                     leaveApproveBtnGap.setVisibility(View.GONE);
                                     leaveApproveBtn.setVisibility(View.GONE);
                                     leaveRejectBtn.setVisibility(View.GONE);

                                 }})
                             .setNegativeButton(android.R.string.no, null).show();

                 }
             });

         }
     }
}
