package com.example.ok_food;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private com.example.ok_food.ShakeDetector mShakeDetector;

    private TextView tvShake;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {   //demo link   http://jasonmcreynolds.com/?p=388
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_reward);

        tvShake = findViewById(R.id.tvShake);
//        btn = findViewById(R.id.btn);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ServiceActivity.class);
//                startActivity(intent);
//            }
//        });

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new com.example.ok_food.ShakeDetector();
        mShakeDetector.setOnShakeListener(new com.example.ok_food.ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                tvShake.setText("key chain by okfood");
                Toast.makeText(MainActivity.this, "Shaked!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
