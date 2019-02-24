package com.example.ok_food;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FListAdapter extends RecyclerView.Adapter<FListAdapter.FViewHolder> {

    private final static String TAG = FListAdapter.class.getSimpleName();
    private JSONArray mFoodList;
    private LayoutInflater mInflater;
    private Context context;

    public FListAdapter(Context context, JSONArray argument) {
        mFoodList = argument;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setmFoodList(JSONArray mFoodList) {
        this.mFoodList = mFoodList;
    }

    @NonNull
    @Override
    public FViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.list_item, viewGroup, false);
        return new FViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final FViewHolder viewHolder, int i) {
        try {
            JSONObject food = mFoodList.getJSONObject(i);
            viewHolder.setNama(food.getString("nama"));
            viewHolder.setDesc(food.getString("deskripsi"));
            viewHolder.setHarga(food.getString("harga"));
            viewHolder.imgUrl = food.getString("foto");
            Picasso.get().load(food.getString("foto")).error(R.drawable.nasi_padang_1).placeholder(R.drawable.nasi_padang_1).fit().into(viewHolder.image);

            viewHolder.help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String param = viewHolder.getNama().replace("\\s", "");
                    String url = "http://www.google.com/search?q=" + param;
                    Uri webpage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    context.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            Log.v(TAG, "Error parsing JSONObject");
        }
    }

    @Override
    public int getItemCount() {
        return mFoodList.length();
    }

    class FViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final FListAdapter mAdapter;
        private TextView nama;
        private TextView desc;
        private TextView harga;
        public ImageView image;
        private TextView quantity;
        private Button plus;
        private Button minus;
        public String imgUrl;
        public Button help;

        FViewHolder(View itemView, FListAdapter adapter) {
            super(itemView);
            nama = itemView.findViewById(R.id.food_name);
            desc = itemView.findViewById(R.id.food_desc);
            harga = itemView.findViewById(R.id.food_cost);
            image = itemView.findViewById(R.id.gambar_makanan);
            quantity = itemView.findViewById(R.id.quantity);

            plus = itemView.findViewById(R.id.button_plus);
            minus = itemView.findViewById(R.id.button_minus);

            itemView.setOnClickListener(this);
            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
            imgUrl = "";

            help = itemView.findViewById(R.id.search);

            this.mAdapter = adapter;
        }

        String getNama() {
            return this.nama.getText().toString();
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

        void decrement() {
            int qty = Integer.parseInt(quantity.getText().toString());
            if (qty <= 0) {
                quantity.setText("0");
            } else {
                qty--;
                quantity.setText(String.valueOf(qty));
            }
        }

        void increment() {
            int qty = Integer.parseInt(quantity.getText().toString());
            qty++;
            quantity.setText(String.valueOf(qty));
        }

        @Override
        public void onClick(View v) {
            if (v.getId() ==  R.id.button_plus) {
                increment();
                broadCast(v);
            } else if (v.getId() == R.id.button_minus) {
                decrement();
                broadCast(v);
            }
        }


        void broadCast(View v) {
            String name = nama.getText().toString();
            String qty = quantity.getText().toString();
            String cost = harga.getText().toString();
            Intent intent = new Intent("ItemQuantity");
            intent.putExtra("name", name);
            intent.putExtra("quantity", qty);
            intent.putExtra("cost", cost);
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

        }
    }
}
