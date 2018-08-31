package com.ammar.saifiyahschool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentLeaveAdapter extends RecyclerView.Adapter<StudentLeaveAdapter.StudentLeaveViewHandler> {

   ArrayList<StudentLeaveData> studentListLeaveData;
   Context StudentLeaveContext;

    public StudentLeaveAdapter(Context StudentLeaveContext, ArrayList<StudentLeaveData> studentListLeaveData) {
        this.StudentLeaveContext=StudentLeaveContext;
        this.studentListLeaveData = studentListLeaveData;
    }

    @NonNull
    @Override
    public StudentLeaveViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_leave_recyclerview, parent, false);

        return new StudentLeaveViewHandler(itemView);
}

    @Override
    public void onBindViewHolder(@NonNull StudentLeaveViewHandler holder, int position) {

      holder.leaveNo.setText(Integer.toString(studentListLeaveData.get(position).getLeaveNo()));
      holder.leaveMonth.setText(studentListLeaveData.get(position).getLeaveMonth());
      holder.leaveDays.setText(Integer.toString(studentListLeaveData.get(position).getLeaveDays()));

    }

    @Override
    public int getItemCount() {
        return studentListLeaveData.size();
    }

   class StudentLeaveViewHandler extends RecyclerView.ViewHolder  {

        TextView leaveNo,leaveMonth,leaveDays;

       public StudentLeaveViewHandler(@NonNull View itemView) {
           super(itemView);

           leaveNo = itemView.findViewById(R.id.leaveNo);
           leaveMonth= itemView.findViewById(R.id.leaveMonth);
           leaveDays= itemView.findViewById(R.id.leaveDay);
       }
   }
}
