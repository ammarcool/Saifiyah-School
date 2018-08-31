package com.ammar.saifiyahschool;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Fees extends AppCompatActivity {

    private ArrayList<FeesTransactionData> feesTransactionDataArrayList = new ArrayList<>();
    private RecyclerView feesRecyclerview;
    private FeesTransactionAdapter feesTransactionAdapter;

    TextView totalFees,totalYearRupeesIcon,feesDue;
    Typeface totalfeesfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        totalFees =(TextView)findViewById(R.id.totalAmount);
        feesRecyclerview =(RecyclerView)findViewById(R.id.fees_recyclerView);
        totalYearRupeesIcon =(TextView)findViewById(R.id.totalYearRupeesIcon);
        feesDue = (TextView)findViewById(R.id.dueAmount);

        totalFees.setText("16,000");
        feesDue.setText("14,000");

        //Font
        totalfeesfont = Typeface.createFromAsset(getAssets(), "fonts/cabinsketch.otf");
        totalFees.setTypeface(totalfeesfont);
        totalYearRupeesIcon.setTypeface(totalfeesfont);

        feesTransactionAdapter = new FeesTransactionAdapter(feesTransactionDataArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        feesRecyclerview.setLayoutManager(mLayoutManager);
        feesRecyclerview.setAdapter(feesTransactionAdapter);

        addFeesTransactionData();
    }

    private void addFeesTransactionData() {

        FeesTransactionData feesTransactionData = new FeesTransactionData("1st Tearm Fees","12-dec-2018",6000,10000);
        feesTransactionDataArrayList.add(feesTransactionData);

        feesTransactionData = new FeesTransactionData("2nd Tearm Fees","22-Jan-2019",1000,8000);
        feesTransactionDataArrayList.add(feesTransactionData);


        feesTransactionAdapter.notifyDataSetChanged();
    }


}
