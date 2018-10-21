package com.ammar.saifiyahschool.teachers.AddClassTest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ammar.saifiyahschool.R;

import java.util.ArrayList;
import java.util.List;

public class classNameAdapter extends ArrayAdapter<classSubjectData>{

    private List<classSubjectData> classSubjectDataList = new ArrayList<>();

    public classNameAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<classSubjectData> objects) {
        super(context, resource, textViewResourceId, objects);
        this.classSubjectDataList = objects;
    }

    @Nullable
    @Override
    public classSubjectData getItem(int position) {
        return classSubjectDataList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    private View initView(int position) {
        classSubjectData classSubjectData = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.class_name, null);
        TextView textView =  v.findViewById(R.id.className);
        textView.setText(classSubjectData.getClassName());
        return v;

    }
}
