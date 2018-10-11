package com.ammar.saifiyahschool.teachers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ammar.saifiyahschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class viewClassTest extends Fragment {


    public viewClassTest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_class_test, container, false);
    }

}
