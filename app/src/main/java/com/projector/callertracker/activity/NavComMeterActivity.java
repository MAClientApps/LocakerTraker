package com.projector.callertracker.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;

import edu.arbelkilani.compass.Compass;
import edu.arbelkilani.compass.CompassListener;


public class NavComMeterActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_com_meter);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RelativeLayout rl_adplaceholder = findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, NavComMeterActivity.this,0);


        Compass compass = findViewById(R.id.compass_view);
        compass.setListener(new CompassListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.d("TAG", "onSensorChanged : " + event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d("TAG", "onAccuracyChanged : sensor : " + sensor);
                Log.d("TAG", "onAccuracyChanged : accuracy : " + accuracy);
            }
        });

    }

}
