package com.example.ok_food;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FListAdapter extends RecyclerView.Adapter<FListAdapter.FoodViewHolder> {

    class FoodViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nama;
        public TextView deskripsi;
        public Button buy;
        final FListAdapter adapter;

        public FoodViewHolder(@NonNull View itemView, FListAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }


    }

    @NonNull
    @Override
    public FListAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FListAdapter.FoodViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
