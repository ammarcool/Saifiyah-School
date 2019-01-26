package com.ammar.saifiyahschool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentprogressAdapter extends RecyclerView.Adapter<StudentprogressAdapter.StudentProgressViewHolder> {

    ArrayList<StudentProgressData> studentProgressDataList;
    Context studentProgressContext;

    public StudentprogressAdapter(ArrayList<StudentProgressData> studentProgressDataList, Context studentProgressContext) {
        this.studentProgressDataList = studentProgressDataList;
        this.studentProgressContext = studentProgressContext;
    }

    @NonNull
    @Override
    public StudentProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_progress_recyclerview, parent, false);
        return new StudentProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentProgressViewHolder holder, int position) {

        holder.progressCard.setBackgroundResource(studentProgressDataList.get(position).getCard());
        holder.progressDate.setText(studentProgressDataList.get(position).getProgressDate());
        holder.progressDesc.setText(studentProgressDataList.get(position).getProgressDesc());

        if (studentProgressDataList.get(position).getCard() == R.drawable.greencard) {
            holder.suggestionText.setText("Really Amazing Job!");
            holder.cardMainText.setText("Congratulation");

        } else {
            holder.suggestionText.setText("Stop Doing This Activity");
            holder.cardMainText.setText("Bad Habits");
            holder.cardMainText.setTextSize(30);
        }

    }

    @Override
    public int getItemCount() {
        return studentProgressDataList.size();
    }


    class StudentProgressViewHolder extends RecyclerView.ViewHolder {

        LinearLayout progressCard;
        TextView progressDate;
        TextView progressDesc;
        TextView suggestionText, cardMainText;

        public StudentProgressViewHolder(@NonNull View itemView) {
            super(itemView);

            progressCard = itemView.findViewById(R.id.progressCard);
            progressDate = itemView.findViewById(R.id.progress_date);
            progressDesc = itemView.findViewById(R.id.progressDesc);
            suggestionText = itemView.findViewById(R.id.suggestionText);
            cardMainText = itemView.findViewById(R.id.cardMainText);
        }
    }
}
