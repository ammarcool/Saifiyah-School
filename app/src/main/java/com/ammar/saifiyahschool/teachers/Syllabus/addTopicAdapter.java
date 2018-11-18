package com.ammar.saifiyahschool.teachers.Syllabus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;

import java.util.ArrayList;

public class addTopicAdapter extends RecyclerView.Adapter<addTopicAdapter.addTopicViewHolder> {

    ArrayList<addTopicsData> addTopicsDataArrayList;
    Context context;

    public addTopicAdapter(ArrayList<addTopicsData> addTopicsDataArrayList, Context context) {
        this.addTopicsDataArrayList = addTopicsDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public addTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addsyllabus_recyclerview, parent, false);

        return new addTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addTopicViewHolder holder, int position) {

        holder.syllabusTopicNo.setText(addTopicsDataArrayList.get(position).getSyllabusTopicNo());
        holder.syllabusChapterName.setText(addTopicsDataArrayList.get(position).getSyllabusChapterName());
    }

    @Override
    public int getItemCount() {
        return addTopicsDataArrayList.size();
    }

    class addTopicViewHolder extends RecyclerView.ViewHolder {

        TextView syllabusTopicNo;
        EditText syllabusChapterName;

        public addTopicViewHolder(@NonNull View itemView) {
            super(itemView);

            syllabusTopicNo = itemView.findViewById(R.id.syllabusTopicNo);
            syllabusChapterName = itemView.findViewById(R.id.syllabusChapterName);

            syllabusChapterName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    addTopicsDataArrayList.get(getAdapterPosition()).setSyllabusChapterName(syllabusChapterName.getText().toString());
                    Toast.makeText(context,syllabusChapterName.getText().toString(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
