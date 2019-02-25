package com.example.ok_food;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    Map<String, Map.Entry<String, String>> itemBought;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tool_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemBought = new HashMap<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_makanan));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_minuman));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.

        // Using PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        // This is another example of the adapter pattern.
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // Setting a listener for clicks.

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        NotificationReceiver notifier = new NotificationReceiver(this);
        notifier.execute();

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String ItemName = intent.getStringExtra("name");
                String qty = intent.getStringExtra("quantity");
                String cost = intent.getStringExtra("cost");
                //Toast.makeText(MainActivity.this,ItemName +" "+qty + " " + cost, Toast.LENGTH_SHORT).show();
                Map.Entry<String, String> quantitycost = new AbstractMap.SimpleEntry<>(qty, cost);
                itemBought.put(ItemName, quantitycost);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ItemQuantity"));
    }

    public void signOut(MenuItem item) {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void orderNow(MenuItem item) {
        boolean exist = false;
        for (Map.Entry<String, Map.Entry<String, String>> e : itemBought.entrySet()) {
            if (Integer.parseInt(e.getValue().getKey()) != 0) {
                exist = true;
                break;
            }
        }
        if (exist) {
            Intent intent = new Intent(this, order_activity.class);
            intent.putExtra("order", (Serializable) itemBought);
            startActivity(intent);
        }
    }
}