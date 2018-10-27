package com.ammar.saifiyahschool.teachers.AddClassTest;

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

import com.ammar.saifiyahschool.R;

import java.util.ArrayList;

public class addCTMarksAdapter extends RecyclerView.Adapter<addCTMarksAdapter.addCTViewHolder> {

    ArrayList<addCTMarksData> addCTMarksDataArrayList;
    Context context;

    public addCTMarksAdapter(ArrayList<addCTMarksData> addCTMarksDataArrayList, Context context) {
        this.addCTMarksDataArrayList = addCTMarksDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public addCTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addclasstest_recyclerview, parent, false);

        return new addCTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addCTViewHolder holder, int position) {

        holder.serialNoofSt.setText(addCTMarksDataArrayList.get(position).getStudentRollno());
        holder.nameOfSt.setText(addCTMarksDataArrayList.get(position).getStudentCTName());
        holder.enterCtMarks.setText(addCTMarksDataArrayList.get(position).getEnterCT());
//        holder.enterCtMarks.setId(position);
        holder.nextLine.findViewById(R.id.nextLIne);

    }

    @Override
    public int getItemCount() {
        return addCTMarksDataArrayList.size();
    }

    class addCTViewHolder extends RecyclerView.ViewHolder {

        TextView serialNoofSt;
        TextView nameOfSt;
        EditText enterCtMarks;
        View nextLine;

        public addCTViewHolder(@NonNull View itemView) {
            super(itemView);

            serialNoofSt = itemView.findViewById(R.id.studentNo);
            nameOfSt = itemView.findViewById(R.id.CTstudentNames);
            enterCtMarks = itemView.findViewById(R.id.enterStudentMarks);
            nextLine = itemView.findViewById(R.id.nextLIne);

            enterCtMarks.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    addCTMarksDataArrayList.get(getAdapterPosition()).setEnterCT(enterCtMarks.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
