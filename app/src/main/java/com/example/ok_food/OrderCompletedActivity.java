package com.example.ok_food;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OrderCompletedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_complete);

        Intent intent = new Intent("order confirmed");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
