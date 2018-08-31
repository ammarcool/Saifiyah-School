package com.ammar.saifiyahschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class studentLeave extends AppCompatActivity {

    private ArrayList<StudentLeaveData> studentLeaveDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentLeaveAdapter studentLeaveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave);

        recyclerView=(RecyclerView)findViewById(R.id.studentLeave_recyclerview);

        studentLeaveAdapter = new StudentLeaveAdapter(this,studentLeaveDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(studentLeaveAdapter);

        addStudentLeave();
    }

    private void addStudentLeave() {

        StudentLeaveData studentLeaveData=new StudentLeaveData(01,"Dec",4);
        studentLeaveDataList.add(studentLeaveData);

        studentLeaveData = new StudentLeaveData(02, "Jan", 2 );
        studentLeaveDataList.add(studentLeaveData);

        studentLeaveData = new StudentLeaveData(03, "Mar", 5);
        studentLeaveDataList.add(studentLeaveData);

        studentLeaveAdapter.notifyDataSetChanged();
    }
}
