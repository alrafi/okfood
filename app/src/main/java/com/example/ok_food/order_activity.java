package com.example.ok_food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class order_activity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_activity);

        Toolbar toolbar = findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        Map<String, Map.Entry<String, String>> arg = (Map<String, Map.Entry<String, String>>) intent.getSerializableExtra("order");

        Map<String, Map.Entry<String, String>> newarg = new HashMap<>();
        //Remove product with zero quantity
        for (Map.Entry<String, Map.Entry<String, String>> e : arg.entrySet()) {
            if (Integer.parseInt(e.getValue().getKey()) != 0) {
                newarg.put(e.getKey(), e.getValue());
            }
        }

        int totaaal = 0;
        for (Map.Entry<String, Map.Entry<String, String>> e : arg.entrySet()) {
            totaaal += Integer.parseInt(e.getValue().getValue()) * Integer.parseInt(e.getValue().getKey());
        }

        TextView totalharga = findViewById(R.id.total_harga);
        totalharga.setText(String.valueOf(totaaal));

        RecyclerView mRecyclerView = findViewById(R.id.order_recycler_view);
        OrderAdapter mAdapter = new OrderAdapter(this, newarg);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void confirmOrder(View view) {
        Intent intent = new Intent(this, OrderCompletedActivity.class);
        startActivity(intent);
        finish();
    }
}
