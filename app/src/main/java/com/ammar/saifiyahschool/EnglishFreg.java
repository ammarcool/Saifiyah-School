package com.ammar.saifiyahschool;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishFreg extends Fragment {

    View v;
    RecyclerView recyclerView ;
    private ArrayList<SyllabusData> syllabusData;

    public EnglishFreg() {
        // Required empty public constructor
    }

    LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        syllabusAdapter syllabusAdapter = new syllabusAdapter(getContext(),syllabusData);


        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_english_freg, container, false);
        recyclerView = v.findViewById(R.id.English_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(syllabusAdapter);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        syllabusData = new ArrayList<>();
        syllabusData.add(new SyllabusData("Feb","1st Unit","hello guys how are you ?",R.drawable.done));
        syllabusData.add (new SyllabusData("Mar","2nd Unit","omg! this is a second unit",R.drawable.progress));

    }
}
