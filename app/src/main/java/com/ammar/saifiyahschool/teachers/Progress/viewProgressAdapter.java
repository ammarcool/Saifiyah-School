package com.ammar.saifiyahschool.teachers.Progress;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ammar.saifiyahschool.R;

import java.util.ArrayList;

public class viewProgressAdapter extends RecyclerView.Adapter<viewProgressHolder> {

    ArrayList<viewProgressData> viewProgressDataArrayList;
    Context context;
    int mExpandedPosition = -1;
    int previousExpandedPosition = -1;

    public viewProgressAdapter(ArrayList<viewProgressData> viewProgressDataArrayList, Context context) {
        this.viewProgressDataArrayList = viewProgressDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewProgressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_progress_card_recyclerview,parent,false);

        return new viewProgressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewProgressHolder holder, final int position) {

        holder.progressViewDate.setText(viewProgressDataArrayList.get(position).getStudentProgressDay());
        holder.progressViewMonth.setText(viewProgressDataArrayList.get(position).getStudentProgressmonth());
        holder.progressStudentName.setText(viewProgressDataArrayList.get(position).getStudentProgressName());
        holder.studentProgressClass.setText(viewProgressDataArrayList.get(position).getStudentProgressClass());
        holder.progressDescription.setText(viewProgressDataArrayList.get(position).getStudentProgresstitle());
        holder.drop_down_icon.setImageResource(R.drawable.drop_down);
        if (viewProgressDataArrayList.get(position).getStudentProgressReward() == String.valueOf(true)){
            holder.myRelative.setBackgroundResource(R.drawable.greencard_bg);
            holder.noOfCards.setText("Rewards");
        }else {
            holder.myRelative.setBackgroundResource(R.drawable.redcard_bg);
            holder.noOfCards.setText("Complain");
        }

        final boolean isExpanded = position==mExpandedPosition;
        holder.studentProgressClass.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.progressClass.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.studentProgressLine.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.progressDescription.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                holder.drop_down_icon.setImageResource(R.drawable.collapse_less);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return viewProgressDataArrayList.size();
    }


}

class viewProgressHolder extends RecyclerView.ViewHolder{

    TextView progressViewDate;
    TextView progressViewMonth;
    TextView progressStudentName;
    ImageView drop_down_icon;
    TextView noOfCards;
    TextView studentProgressClass;
    TextView progressDescription;
    ImageView progress_img_bg;
    TextView progressClass;
    ImageView studentProgressLine;

    RelativeLayout myRelative;

    public viewProgressHolder(@NonNull View itemView) {
        super(itemView);

        progressViewDate = itemView.findViewById(R.id.progressViewDate);
        progressViewMonth = itemView.findViewById(R.id.progressViewMonth);
        progressStudentName = itemView.findViewById(R.id.progressStudentName);
        drop_down_icon = itemView.findViewById(R.id.drop_down_icon);
        noOfCards = itemView.findViewById(R.id.noOfCards);
        studentProgressClass = itemView.findViewById(R.id.studentProgressClass);
        progressDescription = itemView.findViewById(R.id.progressDescription);
//        progress_img_bg = itemView.findViewById(R.id.progress_img_bg);
        myRelative = itemView.findViewById(R.id.myRelative);
        progressClass =itemView.findViewById(R.id.progressClass);
        studentProgressLine = itemView.findViewById(R.id.studentProgressLine);

    }
}
