package com.ammar.saifiyahschool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentprogressAdapter extends RecyclerView.Adapter<StudentProgressViewHolder> {

    ArrayList<StudentProgressData>  studentProgressDataList;
    Context studentProgressContext;

    public StudentprogressAdapter(ArrayList<StudentProgressData> studentProgressDataList, Context studentProgressContext) {
        this.studentProgressDataList = studentProgressDataList;
        this.studentProgressContext = studentProgressContext;
    }

    @NonNull
    @Override
    public StudentProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_progress_recyclerview,parent,false);
        return new StudentProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentProgressViewHolder holder, int position) {

        holder.progressCard.setImageResource(studentProgressDataList.get(position).getCard());
        holder.progressDate.setText(studentProgressDataList.get(position).getProgressDate());
        holder.progressDesc.setText(studentProgressDataList.get(position).getProgressDesc());

    }

    @Override
    public int getItemCount() {
        return studentProgressDataList.size();
    }

}
class StudentProgressViewHolder extends RecyclerView.ViewHolder {

    ImageView progressCard;
    TextView progressDate;
    TextView progressDesc;

    public StudentProgressViewHolder(@NonNull View itemView) {
        super(itemView);

        progressCard=itemView.findViewById(R.id.progressCard);
        progressDate = itemView.findViewById(R.id.progress_date);
        progressDesc = itemView.findViewById(R.id.progressDesc);
    }
}
