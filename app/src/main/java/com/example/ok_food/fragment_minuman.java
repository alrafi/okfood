package com.example.ok_food;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_minuman extends Fragment {
    private final static String TAG = fragment_minuman.class.getSimpleName();
    private JSONArray argument;


    public fragment_minuman() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        argument = new JSONArray();

        View view = inflater.inflate(R.layout.fragment_fragment_makanan, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerview);
        final FListAdapter mAdapter = new FListAdapter(getContext(), argument);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("drink");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                argument = new JSONArray();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> food_data = (Map<String, Object>) ds.getValue();
                    //Log.d(TAG, "Value is: " + food_data.toString());
                    //Log.d(TAG, "Foto is: " + food_data.get("foto").toString());
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("foto", food_data.get("foto").toString());
                        obj.put("nama", food_data.get("nama").toString());
                        obj.put("deskripsi", food_data.get("deskripsi").toString());
                        obj.put("harga", food_data.get("harga").toString());
                        argument.put(obj);
                    } catch (JSONException e) {
                        Log.w(TAG, "Error parsing JSONArray, food_data Null");
                    }
                }
                mAdapter.setmFoodList(argument);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, argument.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // Inflate the layout for this fragment
        return view;
    }

}
