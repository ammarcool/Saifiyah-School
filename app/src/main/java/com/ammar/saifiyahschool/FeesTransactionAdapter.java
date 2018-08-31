package com.ammar.saifiyahschool;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FeesTransactionAdapter extends RecyclerView.Adapter<FeesTransactionViewHolder> {

    ArrayList<FeesTransactionData> feesTransactionDataArrayList;

    public FeesTransactionAdapter(ArrayList<FeesTransactionData> feesTransactionDataArrayList) {
        this.feesTransactionDataArrayList = feesTransactionDataArrayList;
    }

    @NonNull
    @Override
    public FeesTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fees_transaction_recyclerview,parent,false);
        return new FeesTransactionViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull FeesTransactionViewHolder holder, int position) {

        holder.transactionName.setText(feesTransactionDataArrayList.get(position).getTransactionName());
        holder.transactionDate.setText(feesTransactionDataArrayList.get(position).getTransactionDate());
        holder.transactionAmount.setText(Integer.toString(feesTransactionDataArrayList.get(position).getTransactionAmount()));
//        holder.amountDue.setText(feesTransactionDataArrayList.get(position).getFeesDue());

        holder.dateIcon.setImageResource(R.drawable.date);
//        holder.rupeesIcons.setText(R.id.rupeesIcon);
        holder.rupeesIcons.findViewById(R.id.rupeesIcon);
    }

    @Override
    public int getItemCount() {
        return feesTransactionDataArrayList.size();
    }
}

class FeesTransactionViewHolder extends RecyclerView.ViewHolder{

    TextView transactionName;
    TextView transactionDate;
    TextView rupeesIcons;
    TextView transactionAmount;
//    TextView amountDue;
    ImageView dateIcon;

    public FeesTransactionViewHolder(@NonNull View itemView) {
        super(itemView);

        transactionName = itemView.findViewById(R.id.transactionName);
        transactionDate = itemView.findViewById(R.id.transactionDate);
        rupeesIcons = itemView.findViewById(R.id.rupeesIcon);
        transactionAmount = itemView.findViewById(R.id.transactionAmount);
        dateIcon = itemView.findViewById(R.id.dateIcon);
//        amountDue =itemView.findViewById(R.id.dueAmount);
    }
}