package com.example.ok_food;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private LayoutInflater mInflater;
    private Map<String, Map.Entry<String, String>> data;
    private static final String TAG = OrderAdapter.class.getSimpleName();

    public OrderAdapter(Context context, Map<String, Map.Entry<String, String>> argument) {
        this.data = argument;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.list_order, viewGroup, false);
        return new OrderHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder orderHolder, int i) {
        int j = 0;
        Map.Entry<String, Map.Entry<String, String>> ithElement = null;
        for (Map.Entry<String, Map.Entry<String, String>> e : data.entrySet()) {
            if (j == i) {
                ithElement = new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue());
                break;
            }
            j++;
        }
        Log.d(TAG, ithElement.toString());
        orderHolder.setNama(ithElement.getKey());
        orderHolder.setJumlah(ithElement.getValue().getKey());
        orderHolder.setHarga(ithElement.getValue().getValue());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder {

        private TextView nama;
        private TextView jumlah;
        private TextView harga;
        private OrderAdapter mAdapter;

        public OrderHolder(@NonNull View itemView, OrderAdapter adapter) {
            super(itemView);
            this.nama = itemView.findViewById(R.id.nama_produk);
            this.jumlah = itemView.findViewById(R.id.jumlah_produk);
            this.harga = itemView.findViewById(R.id.harga);
            this.mAdapter = adapter;
        }

        String getHarga() {
            return harga.getText().toString();
        }

        void setNama(String nama) {
            this.nama.setText(nama);
        }

        void setJumlah(String jumlah) {
            this.jumlah.setText(jumlah);
        }

        void setHarga(String harga) {
            this.harga.setText(harga);
        }
    }
}
