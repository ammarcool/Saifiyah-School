package com.ammar.saifiyahschool;

import android.content.Context;
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

    public syllabusAdapter(Context syllabusContext,ArrayList<SyllabusData> syllabusListData){

        this.syllabusContext = syllabusContext;
        this.syllabusListData = syllabusListData;
    }

    @Override
    public SyllabusViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabus_recyclerview,parent,false);
        return new SyllabusViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SyllabusViewHolder holder, int position) {
//
//        SyllabusData syllabusData = syllabusListData.get(position);
//        holder.month.setText(syllabusData.getMonth());
//        holder.UnitName.setText(syllabusData.getUnitName());
        holder.syllabusMonth.setText(syllabusListData.get(position).getSyllabusMonth());
        holder.syllabusDay.setText(syllabusListData.get(position).getSyllabusDay());
        holder.SyllabusStudentChapterNo.setText(syllabusListData.get(position).getSyllabusStudentChapterNo());
        holder.syllabusName.setText(syllabusListData.get(position).getSyllabusName());

        if (syllabusListData.get(position).getSyllabusStatus()== "true"){
            holder.syllabusStatus.setImageResource(R.drawable.done_sticker);
        }else {
            holder.syllabusStatus.setImageResource(R.drawable.pending_sticker);
        }

    }

    @Override
    public int getItemCount() {
        return syllabusListData.size();
    }


}
class SyllabusViewHolder extends RecyclerView.ViewHolder{


    TextView syllabusMonth;
    TextView syllabusDay;
    ImageView syllabusStatus;
    TextView syllabusName;
    TextView SyllabusStudentChapterNo;

    public SyllabusViewHolder( View itemView) {
        super(itemView);

     syllabusMonth = itemView.findViewById(R.id.syllabusMonth);
     syllabusDay = itemView.findViewById(R.id.syllabusDay);
     syllabusStatus= itemView.findViewById(R.id.syllabusStatus);
     syllabusName = itemView.findViewById(R.id.syllabusName);
     SyllabusStudentChapterNo = itemView.findViewById(R.id.SyllabusStudentChapterNo);

    }
}