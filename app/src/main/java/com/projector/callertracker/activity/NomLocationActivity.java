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


public class NomLocationActivity extends BaseActivity {

    private LinearLayout llNumberLocator, llLocation, llStdCodes, llIsdCOdes;
    ImageView ivNumberLocation, ivMobileTools, ivSimInfomatoin, ivBankInfomation, ivNearBy, ivFindTrafic, ivNavCompass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_location);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        llNumberLocator = findViewById(R.id.llNumberLocator);
        llLocation = findViewById(R.id.llLocation);
        llStdCodes = findViewById(R.id.llStdCodes);
        llIsdCOdes = findViewById(R.id.llIsdCOdes);

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

        ivNumberLocation.setVisibility(View.GONE);

        ivMobileTools.setOnClickListener(mListener);
        ivSimInfomatoin.setOnClickListener(mListener);
        ivBankInfomation.setOnClickListener(mListener);
        ivNearBy.setOnClickListener(mListener);
        ivFindTrafic.setOnClickListener(mListener);
        ivNavCompass.setOnClickListener(mListener);

        RelativeLayout rl_adplaceholder = findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, NomLocationActivity.this,1);

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionLocation(NomLocationActivity.this)) {
                    toLocation();
                } else {
                    requestPermissionLocation(NomLocationActivity.this, 111);
                }
            }
        });
        llNumberLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NomLocationActivity.this, NomLocatorActivity.class);

                MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent, false);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
        llIsdCOdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NomLocationActivity.this, ISDCodeActivity.class);

                MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent, false);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toLocation();
                } else {
                    Toast.makeText(NomLocationActivity.this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
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


    public void toLocation() {
        Intent intent = new Intent(NomLocationActivity.this, LocationActivity.class);

        MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent, false);
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivMobileTools:

                    Intent intent4 = new Intent(NomLocationActivity.this, PhoneToolActivity.class);

                    MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent4, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivSimInfomatoin:
                    Intent intent3 = new Intent(NomLocationActivity.this, SimInfomationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent3, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivBankInfomation:
                    Intent intent2 = new Intent(NomLocationActivity.this, BankInfomationActivity.class);

                    MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent2, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivNearBy:
                    Intent intent6 = new Intent(NomLocationActivity.this, FindTraficActivity.class);

                    MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent6, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
                case R.id.ivFindTrafic:
                    if (checkPermissionLocation(NomLocationActivity.this)) {
                        toLocation();
                    } else {
                        requestPermissionLocation(NomLocationActivity.this, 111);
                    }
                    break;
                case R.id.ivNavCompass:
                    Intent intent7 = new Intent(NomLocationActivity.this, CompassActivity.class);

                    MainApp.getInstance().displayInterstitialAds(NomLocationActivity.this, intent7, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                    break;
            }
        }
    };

}
