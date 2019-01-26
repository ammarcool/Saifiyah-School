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

public class syllabusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SyllabusData> syllabusListData;
    private Context syllabusContext;

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    public syllabusAdapter(Context syllabusContext,ArrayList<SyllabusData> syllabusListData){

        this.syllabusContext = syllabusContext;
        this.syllabusListData = syllabusListData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_header_recyclerview, parent, false);
            return new SyllabusViewHolderFirst(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabus_recyclerview,parent,false);
            return new SyllabusViewHolder(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }


    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
//
//        SyllabusData syllabusData = syllabusListData.get(position);
//        holder.month.setText(syllabusData.getMonth());
//        holder.UnitName.setText(syllabusData.getUnitName());

        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((SyllabusViewHolderFirst) holder, position);
                break;
            case TYPE_TWO:
                initLayoutTwo((SyllabusViewHolder) holder, position);
                break;
            default:
                break;
        }

    }

    private void initLayoutOne(SyllabusViewHolderFirst holder, int position) {
        holder.examinationName.setText(syllabusListData.get(position).getExaminationName());

    }

    private void initLayoutTwo(SyllabusViewHolder holder, int position) {
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
        return syllabusListData== null ? 0 : syllabusListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        SyllabusData addSyllabusData = syllabusListData.get(position);
        if (addSyllabusData.getType() == SyllabusData.StudentItemType.ONE_ITEM) {
            return TYPE_ONE;
        } else if (addSyllabusData.getType() == SyllabusData.StudentItemType.TWO_ITEM) {
            return TYPE_TWO;
        } else {
            return -1;
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

class SyllabusViewHolderFirst extends RecyclerView.ViewHolder{

    TextView examinationName;

    public SyllabusViewHolderFirst(@NonNull View itemView) {
        super(itemView);
        examinationName = itemView.findViewById(R.id.examinationName);
    }
}

}