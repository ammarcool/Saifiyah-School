package com.ammar.saifiyahschool;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class FeesTransactionAdapter extends RecyclerView.Adapter<FeesTransactionAdapter.FeesTransactionViewHolder> {

    ArrayList<FeesTransactionData> feesTransactionDataArrayList;
    AnimationDrawable anim;

    public FeesTransactionAdapter(ArrayList<FeesTransactionData> feesTransactionDataArrayList) {
        this.feesTransactionDataArrayList = feesTransactionDataArrayList;
    }

    @NonNull
    @Override
    public FeesTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fees_transaction_recyclerview, parent, false);
        return new FeesTransactionViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull FeesTransactionViewHolder holder, int position) {

        holder.transactionName.setText(feesTransactionDataArrayList.get(position).getTransactionName());
        holder.transactionDate.setText(feesTransactionDataArrayList.get(position).getTransactionStartDate());

        holder.transactionAmount.setText(feesTransactionDataArrayList.get(position).getTransactionAmount());

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
//        try {
//            Date strDate = sdf.parse(feesTransactionDataArrayList.get(position).getTransactionEndDate());
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.i("Error-->", String.valueOf(e));
//        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date end_date = null;
        Date submitted_date = null;
        Date start_date = null;
        try {
            start_date = df.parse(feesTransactionDataArrayList.get(position).getTransactionStartDate());
            Log.i("Start_date-->", feesTransactionDataArrayList.get(position).getTransactionStartDate());
            end_date = df.parse(feesTransactionDataArrayList.get(position).getTransactionEndDate());
            System.out.println("date:" + end_date); //prints date in current locale
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            System.out.println(sdf.format(end_date));
            Log.i("Date-->", sdf.format(end_date));//prints date in the format sdf

            holder.end_date.setText(sdf.format(end_date));
            holder.transactionDate.setText(sdf.format(start_date));

            if (feesTransactionDataArrayList.get(position).getTransactionSubmitDate().equals("null") && new Date().after(start_date) && new Date().before(end_date)) {

                holder.submitted_fee_date.setText("Pay Now");
                holder.fees_sticker.setImageResource(R.drawable.add_alert);

                holder.fees_status_layout.setBackgroundResource(R.drawable.animation_list);
                anim = (AnimationDrawable) holder.fees_status_layout.getBackground();
                anim.setEnterFadeDuration(2500);
                anim.setExitFadeDuration(5000);
                anim.start();

            } else if (feesTransactionDataArrayList.get(position).getTransactionSubmitDate().equals("null") && new Date().after(end_date)) {

                holder.submitted_fee_date.setText("");
                holder.fees_sticker.setImageResource(R.drawable.pending_sticker);




            } else if (feesTransactionDataArrayList.get(position).getTransactionSubmitDate().equals("null")) {
                holder.submitted_fee_date.setText("Still Getting Time");
                holder.fees_sticker.setVisibility(View.INVISIBLE);
            } else {
                submitted_date = df.parse(feesTransactionDataArrayList.get(position).getTransactionSubmitDate());
                holder.submitted_fee_date.setText(sdf.format(submitted_date));
                holder.fees_sticker.setImageResource(R.drawable.done_sticker);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return feesTransactionDataArrayList.size();
    }


    class FeesTransactionViewHolder extends RecyclerView.ViewHolder {

        TextView transactionName;
        TextView transactionDate;
        TextView rupeesIcons;
        TextView transactionAmount;
        TextView end_date;
        TextView submitted_fee_date;
        ImageView fees_sticker;
        LinearLayout fees_status_layout;
        //    TextView amountDue;
        ImageView dateIcon;


        public FeesTransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionName = itemView.findViewById(R.id.transactionName);
            transactionDate = itemView.findViewById(R.id.transactionDate);
            end_date = itemView.findViewById(R.id.end_date);
            rupeesIcons = itemView.findViewById(R.id.rupeesIcon);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
            dateIcon = itemView.findViewById(R.id.dateIcon);
            submitted_fee_date = itemView.findViewById(R.id.submitted_fee_date);
            fees_sticker = itemView.findViewById(R.id.fees_sticker);
            fees_status_layout = itemView.findViewById(R.id.fees_status_layout);
//        amountDue =itemView.findViewById(R.id.dueAmount);

//            anim.start();
        }
    }
}