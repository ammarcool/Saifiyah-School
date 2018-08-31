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
import java.util.List;

public class syllabusAdapter extends RecyclerView.Adapter<SyllabusViewHolder> {

    private ArrayList<SyllabusData> syllabusListData;
    private Context syllabusContext;

    public syllabusAdapter(Context syllabusContext,List syllabusListData){

        this.syllabusContext = syllabusContext;
        this.syllabusListData = (ArrayList<SyllabusData>) syllabusListData;
    }

    @Override
    public SyllabusViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabus_recyclerview,parent,false);
        return new SyllabusViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SyllabusViewHolder holder, int position) {

        SyllabusData syllabusData = syllabusListData.get(position);
        holder.month.setText(syllabusData.getMonth());
        holder.UnitName.setText(syllabusData.getUnitName());

        //or you can write code like that as well
        holder.UnitDesc.setText(syllabusListData.get(position).getUnitDesc());
        holder.status.setImageResource(syllabusListData.get(position).getStatus());

//        String month = syllabusListData.get(position);
//            holder.month.setText(month);
//            holder.UnitDesc.setText(month);
//            holder.UnitName.setText(month);
    }

    @Override
    public int getItemCount() {
        return syllabusListData.size();
    }


}
class SyllabusViewHolder extends RecyclerView.ViewHolder{

    TextView month;
    ImageView status;
    TextView UnitName;
    TextView UnitDesc;

    public SyllabusViewHolder( View itemView) {
        super(itemView);

        month = itemView.findViewById(R.id.tvIcon);
        status =itemView.findViewById(R.id.status);
        UnitName = itemView.findViewById(R.id.UnitName);
        UnitDesc = itemView.findViewById(R.id.UnitDesc);
    }
}