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
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;


public class CompassActivity extends BaseActivity {

    private CardView compass;
    ImageView ivNumberLocation, ivMobileTools, ivSimInfomatoin, ivBankInfomation, ivNearBy, ivFindTrafic, ivNavCompass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RelativeLayout rl_adplaceholder = findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, CompassActivity.this,1);


        compass = findViewById(R.id.compass);
        ivNumberLocation = findViewById(R.id.ivNumberLocation);
        ivMobileTools = findViewById(R.id.ivMobileTools);
        ivSimInfomatoin = findViewById(R.id.ivSimInfomatoin);
        ivBankInfomation = findViewById(R.id.ivBankInfomation);
        ivNearBy = findViewById(R.id.ivNearBy);
        ivFindTrafic = findViewById(R.id.ivFindTrafic);
        ivNavCompass = findViewById(R.id.ivNavCompass);

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

        ivNavCompass.setVisibility(View.GONE);

        ivNumberLocation.setOnClickListener(mListener);
        ivMobileTools.setOnClickListener(mListener);
        ivSimInfomatoin.setOnClickListener(mListener);
        ivBankInfomation.setOnClickListener(mListener);
        ivNearBy.setOnClickListener(mListener);
        ivFindTrafic.setOnClickListener(mListener);

        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompassActivity.this, NavComMeterActivity.class);

                MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent, false);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
    }


    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivNumberLocation:

                    Intent intent = new Intent(CompassActivity.this, NomLocationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivMobileTools:

                    Intent intent4 = new Intent(CompassActivity.this, PhoneToolActivity.class);

                    MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent4, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivSimInfomatoin:
                    Intent intent3 = new Intent(CompassActivity.this, SimInfomationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent3, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivBankInfomation:
                    Intent intent2 = new Intent(CompassActivity.this, BankInfomationActivity.class);
                    MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent2, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivNearBy:
                    Intent intent6 = new Intent(CompassActivity.this, FindTraficActivity.class);

                    MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent6, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivFindTrafic:
                    if (checkPermissionLocation(CompassActivity.this)) {
                        toLocation();
                    } else {
                        requestPermissionLocation(CompassActivity.this, 111);
                    }
                    break;
            }
        }
    };

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toLocation();
                } else {
                    Toast.makeText(CompassActivity.this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
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

    public void toLocation() {
        Intent intent = new Intent(CompassActivity.this, LocationActivity.class);

        MainApp.getInstance().displayInterstitialAds(CompassActivity.this, intent, false);
        overridePendingTransition(R.anim.enter, R.anim.exit);


    }

}
