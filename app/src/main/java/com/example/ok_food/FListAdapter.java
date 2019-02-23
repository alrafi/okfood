package com.example.ok_food;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FListAdapter extends RecyclerView.Adapter<FListAdapter.FViewHolder> {

    private final static String TAG = FListAdapter.class.getSimpleName();
    private JSONArray mFoodList;
    private LayoutInflater mInflater;

    public FListAdapter(Context context) {
        mFoodList = new JSONArray();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("food");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> food_data = (Map<String, Object>) ds.getValue();
                    //Log.d(TAG, "Value is: " + food_data.toString());
                    Log.d(TAG, "Foto is: " + food_data.get("foto").toString());
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("foto", food_data.get("foto").toString());
                        obj.put("nama", food_data.get("nama").toString());
                        obj.put("deskripsi", food_data.get("deskripsi").toString());
                        obj.put("harga", food_data.get("harga").toString());
                        mFoodList.put(obj);
                    } catch (JSONException e) {
                        Log.w(TAG, "Error parsing JSONArray, food_data Null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public FViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.makananlist_item, viewGroup, false);
        return new FViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FViewHolder viewHolder, int i) {
        Log.d(TAG, mFoodList.toString());
        try {
            JSONObject food = mFoodList.getJSONObject(i);
            Log.d(TAG, "JSONObject Value" + food.toString());
            viewHolder.setNama(food.getString("nama"));
            viewHolder.setDesc(food.getString("deskripsi"));
            viewHolder.setHarga(food.getString("harga"));
        } catch (JSONException e) {
            Log.v(TAG, "Error parsing JSONObject");
        }
    }

    @Override
    public int getItemCount() {
        return mFoodList.length();
    }

    class FViewHolder extends RecyclerView.ViewHolder  {
        final FListAdapter mAdapter;
        private TextView nama;
        private TextView desc;
        private TextView harga;
        private ImageView image;

        FViewHolder(View itemView, FListAdapter adapter) {
            super(itemView);
            nama = itemView.findViewById(R.id.food_name);
            desc = itemView.findViewById(R.id.food_desc);
            harga = itemView.findViewById(R.id.food_cost);
            image = itemView.findViewById(R.id.ok_food_logo);
            this.mAdapter = adapter;
        }

        void setNama(String nama) {
            this.nama.setText(nama);
        }

        void setDesc(String desc) {
            this.desc.setText(desc);
        }

        void setHarga(String harga) {
            this.harga.setText(harga);
        }

    }
}
