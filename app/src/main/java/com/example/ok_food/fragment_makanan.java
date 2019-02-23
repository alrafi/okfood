package com.example.ok_food;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_makanan extends Fragment {

    private String argument;
    public fragment_makanan() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_makanan, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerview);

        FListAdapter mAdapter = new FListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // Inflate the layout for this fragment
        return view;

    }

}
