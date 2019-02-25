package com.example.ok_food;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


public class NotificationReceiver extends AsyncTask<Void, Void, String> {

    private static final String TAG = NotificationReceiver.class.getSimpleName();
    private static final String PRIMARY_CHANNEL_ID = "this_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    private Context context;

    public NotificationReceiver(Context context) {
        this.context = context;
        Log.d(TAG, "AsyncTask created!");
    }

    @Override
    protected String doInBackground(Void... voids) {
        final boolean[] startNotification = {false};
        Log.d(TAG, "Doing background...");
        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                startNotification[0] = true;
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(mBroadcastReceiver, new IntentFilter("order confirmed"));
        while (true) {
            while (!startNotification[0]);
            if (startNotification[0]) {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                createNotification("Food on the way!", "We are processing the food. Sit tight!");
                Log.d(TAG, "First Notification");
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                createNotification("Food arrived", "Thank you for ordering our food!");
                Log.d(TAG, "Second Notification");
                startNotification[0] = false;
            }
        }
        return null;
    };


    public void createNotification(String title, String body) {
        mNotifyManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification);
        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
