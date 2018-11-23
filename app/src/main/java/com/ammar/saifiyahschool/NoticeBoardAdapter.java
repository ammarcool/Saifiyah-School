package com.ammar.saifiyahschool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.NoticeBoardViewHolder> {

    ArrayList<NoticeBoardData> noticeBoardDataArrayList;
    Context context;

    public NoticeBoardAdapter(ArrayList<NoticeBoardData> noticeBoardDataArrayList, Context context) {
        this.noticeBoardDataArrayList = noticeBoardDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticeboard_recyclerview, parent, false);

        return new NoticeBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeBoardViewHolder holder, int position) {
            holder.notificationTeacherName.setText(noticeBoardDataArrayList.get(position).getNotificationTeacherName());
            holder.notificationTime.setText(noticeBoardDataArrayList.get(position).getNotificationTime());
            holder.notificationMsg.setText(noticeBoardDataArrayList.get(position).getNotificationMsg());
    }

    @Override
    public int getItemCount() {
        return noticeBoardDataArrayList.size();
    }

     class NoticeBoardViewHolder extends RecyclerView.ViewHolder {

        TextView notificationTeacherName;
        TextView notificationTime;
        TextView notificationMsg;

        public NoticeBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationTeacherName = itemView.findViewById(R.id.notificationTeacherName);
            notificationTime = itemView.findViewById(R.id.notificationTime);
            notificationMsg = itemView.findViewById(R.id.notificationMsg);

        }
    }
}
