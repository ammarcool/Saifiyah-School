package com.ammar.saifiyahschool.teachers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;

import java.util.ArrayList;

public class viewClassTestAdapter  extends RecyclerView.Adapter<CTViewHolder> {


    ArrayList<viewCTData> ctDataArrayList;
    Context context;

    viewClassTestAdapter(ArrayList<viewCTData> ctDataArrayList, Context context) {
        this.ctDataArrayList = ctDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classtest_view_recyclerview,parent,false);

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

    }

    @Override
    public int getItemCount() {
        return ctDataArrayList.size();
    }
}

class CTViewHolder extends RecyclerView.ViewHolder{

    TextView ctDay;
    TextView ctMonth;
    TextView CTSubject;
    TextView totalCTMarks;
    TextView CTClass;
    Button deleteCT;

    public CTViewHolder(@NonNull View itemView) {
        super(itemView);

        ctDay = itemView.findViewById(R.id.ctDay);
        ctMonth = itemView.findViewById(R.id.ctMonth);
        CTSubject = itemView.findViewById(R.id.CTSubject);
        totalCTMarks = itemView.findViewById(R.id.totalCTMarks);
        CTClass = itemView.findViewById(R.id.CTClass);
        deleteCT = itemView.findViewById(R.id.deleteCT);
    }
}
