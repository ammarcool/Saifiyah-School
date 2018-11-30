package com.ammar.saifiyahschool.teachers.LeaveBalance;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ammar.saifiyahschool.R;

import java.util.ArrayList;

public class viewLeaveBalanceAdapter extends RecyclerView.Adapter<leaveBalanceViewHolder> {

    ArrayList<viewLeaveBalanceData> viewLeaveBalanceDataArrayList ;
    Context context;

    public viewLeaveBalanceAdapter(ArrayList<viewLeaveBalanceData> viewLeaveBalanceDataArrayList, Context context) {
        this.viewLeaveBalanceDataArrayList = viewLeaveBalanceDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public leaveBalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacherleaves_recyclerview,parent,false);

        return new leaveBalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull leaveBalanceViewHolder holder, int position) {

        holder.leaveBalanceDate.setText(viewLeaveBalanceDataArrayList.get(position).getViewLBDay());
        holder.leaveBalanceMonth.setText(viewLeaveBalanceDataArrayList.get(position).getViewLBMonth());
        holder.teacherLeaveBalFromDate.setText(viewLeaveBalanceDataArrayList.get(position).getViewFromDate());
        holder.teacherLeaveBalToDate.setText(viewLeaveBalanceDataArrayList.get(position).getViewToDate());
        holder.noOfLeavesBalance.setText(viewLeaveBalanceDataArrayList.get(position).getViewNoOfLeaves());
        holder.typesOfLeavesStatus.setText(viewLeaveBalanceDataArrayList.get(position).getViewTypesOfLeaves());
        holder.teacherHalfDay.setText(viewLeaveBalanceDataArrayList.get(position).getTeacherHalfDay());

        if (viewLeaveBalanceDataArrayList.get(position).getViewLBStatus()== String.valueOf(0)){
            holder.teacherLeavestatus.setText("Pending");
            holder.teacherLeavestatus.setBackgroundColor(Color.YELLOW);
            holder.leave_bg_img.setImageResource(R.drawable.yellow_bg_leavebalance);
        }
        else if(viewLeaveBalanceDataArrayList.get(position).getViewLBStatus()== String.valueOf(1)){

            holder.teacherLeavestatus.setText("Approved");
            holder.teacherLeavestatus.setBackgroundColor(Color.GREEN);
            holder.leave_bg_img.setImageResource(R.drawable.green_bg_leavebalance);
        }
        else {
            holder.teacherLeavestatus.setText("Rejected");
            holder.teacherLeavestatus.setBackgroundColor(Color.RED);
            holder.leave_bg_img.setImageResource(R.drawable.red_bg_leavebalance);
        }
    }

    @Override
    public int getItemCount() {
        return viewLeaveBalanceDataArrayList.size();
    }
}

class leaveBalanceViewHolder extends RecyclerView.ViewHolder{


    TextView leaveBalanceDate;
    TextView leaveBalanceMonth;
    TextView teacherLeaveBalFromDate;
    TextView teacherLeaveBalToDate;
    TextView noOfLeavesBalance;
    TextView typesOfLeavesStatus;
    TextView teacherLeavestatus;
    TextView teacherHalfDay;
    ImageView leave_bg_img;

    public leaveBalanceViewHolder(@NonNull View itemView) {
        super(itemView);

        leaveBalanceDate = itemView.findViewById(R.id.leaveBalanceDate);
        leaveBalanceMonth = itemView.findViewById(R.id.leaveBalanceMonth);
        teacherLeaveBalFromDate = itemView.findViewById(R.id.teacherLeaveBalFromDate);
        teacherLeaveBalToDate = itemView.findViewById(R.id.teacherLeaveBalToDate);
        noOfLeavesBalance = itemView.findViewById(R.id.noOfLeavesBalance);
        typesOfLeavesStatus = itemView.findViewById(R.id.typesOfLeavesStatus);
        teacherLeavestatus = itemView.findViewById(R.id.teacherLeavestatus);
        teacherHalfDay = itemView.findViewById(R.id.teacherHalfDay);
        leave_bg_img = itemView.findViewById(R.id.leave_bg_img);

    }
}
