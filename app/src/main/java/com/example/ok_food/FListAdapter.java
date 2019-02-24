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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FListAdapter extends RecyclerView.Adapter<FListAdapter.FViewHolder> {

    private final static String TAG = FListAdapter.class.getSimpleName();
    private JSONArray mFoodList;
    private LayoutInflater mInflater;

    public FListAdapter(Context context, JSONArray argument) {
        mFoodList = argument;
        mInflater = LayoutInflater.from(context);
    }

    public void setmFoodList(JSONArray mFoodList) {
        this.mFoodList = mFoodList;
    }

    @NonNull
    @Override
    public FViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.makananlist_item, viewGroup, false);
        return new FViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FViewHolder viewHolder, int i) {
        try {
            JSONObject food = mFoodList.getJSONObject(i);
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
            image = itemView.findViewById(R.id.gambar_makanan);
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

        void setImage(String url) {

        }

    }
}
