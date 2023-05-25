package com.projector.callertracker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;


public class PhoneToolActivity extends BaseActivity {

    private LinearLayout llDeviceInfo, llDataUsage, llAudioManager, llSystemUsage;
    ImageView ivNumberLocation, ivMobileTools, ivSimInfomatoin, ivBankInfomation, ivNearBy, ivFindTrafic, ivNavCompass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_tool);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        llDeviceInfo = findViewById(R.id.llDeviceInfo);
        llDataUsage = findViewById(R.id.llDataUsage);
        llAudioManager = findViewById(R.id.llAudioManager);
        llSystemUsage = findViewById(R.id.llSystemUsage);

        ivNumberLocation = findViewById(R.id.ivNumberLocation);
        ivMobileTools = findViewById(R.id.ivMobileTools);
        ivSimInfomatoin = findViewById(R.id.ivSimInfomatoin);
        ivBankInfomation = findViewById(R.id.ivBankInfomation);
        ivNearBy = findViewById(R.id.ivNearBy);
        ivFindTrafic = findViewById(R.id.ivFindTrafic);
        ivNavCompass = findViewById(R.id.ivNavCompass);
        ivNumberLocation.setOnClickListener(mListener);

        Bitmap b1 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home1, 500, 500);
        ivNumberLocation.setImageBitmap(b1);
        Bitmap b2 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home2, 500, 500);
        ivMobileTools.setImageBitmap(b2);
        Bitmap b3 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home3, 500, 500);
        ivSimInfomatoin.setImageBitmap(b3);
        Bitmap b4 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home4, 500, 500);
        ivBankInfomation.setImageBitmap(b4);
        Bitmap b5 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home5, 500, 500);
        ivNearBy.setImageBitmap(b5);
        Bitmap b6 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home6, 500, 500);
        ivFindTrafic.setImageBitmap(b6);
        Bitmap b7 = decodeSampledBitmapFromResource(getResources(), R.drawable.banner_home7, 500, 500);
        ivNavCompass.setImageBitmap(b7);

        ivMobileTools.setVisibility(View.GONE);

        ivSimInfomatoin.setOnClickListener(mListener);
        ivBankInfomation.setOnClickListener(mListener);
        ivNearBy.setOnClickListener(mListener);
        ivFindTrafic.setOnClickListener(mListener);
        ivNavCompass.setOnClickListener(mListener);

        RelativeLayout rl_adplaceholder = findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, PhoneToolActivity.this,1);

        llDeviceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(PhoneToolActivity.this, PhoneInfomationActivity.class);

                MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent4, false);
                overridePendingTransition(R.anim.enter, R.anim.exit);


            }
        });
        llDataUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        llAudioManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneToolActivity.this, AudioControlActivity.class);

                MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent, false);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
        llSystemUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(PhoneToolActivity.this, SystemUseActivity.class);

                MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent6, false);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

    }

    public void toLocation() {
        Intent intent = new Intent(PhoneToolActivity.this, LocationActivity.class);

        MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent, false);
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toLocation();
                } else {
                    Toast.makeText(PhoneToolActivity.this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public static void requestPermissionLocation(Activity activity, final int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, code);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, code);
        }
    }

    public static boolean checkPermissionLocation(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivNumberLocation:

                    Intent intent = new Intent(PhoneToolActivity.this, NomLocationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivSimInfomatoin:
                    Intent intent3 = new Intent(PhoneToolActivity.this, SimInfomationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent3, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivBankInfomation:
                    Intent intent2 = new Intent(PhoneToolActivity.this, BankInfomationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent2, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivNearBy:
                    Intent intent6 = new Intent(PhoneToolActivity.this, FindTraficActivity.class);

                    MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent6, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivFindTrafic:
                    if (checkPermissionLocation(PhoneToolActivity.this)) {
                        toLocation();
                    } else {
                        requestPermissionLocation(PhoneToolActivity.this, 111);
                    }
                    break;
                case R.id.ivNavCompass:
                    Intent intent7 = new Intent(PhoneToolActivity.this, CompassActivity.class);

                    MainApp.getInstance().displayInterstitialAds(PhoneToolActivity.this, intent7, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
            }
        }
    };


}
