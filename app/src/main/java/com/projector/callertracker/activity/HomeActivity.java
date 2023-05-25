package com.projector.callertracker.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;


public class HomeActivity extends BaseActivity {

    private ImageView ivNumberLocation, ivMobileTools, ivSimInfomatoin,
            ivBankInfomation, ivNearBy, ivFindTrafic, ivNavCompass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RelativeLayout rl_adplaceholder = findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, HomeActivity.this,0);

        ivNumberLocation = findViewById(R.id.ivNumberLocation);
        ivMobileTools = findViewById(R.id.ivMobileTools);
        ivSimInfomatoin = findViewById(R.id.ivSimInfomatoin);
        ivBankInfomation = findViewById(R.id.ivBankInfomation);
        ivNearBy = findViewById(R.id.ivNearBy);
        ivFindTrafic = findViewById(R.id.ivFindTrafic);
        ivNavCompass = findViewById(R.id.ivNavCompass);

        Bitmap b1 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_number_locator, 500, 500);
        ivNumberLocation.setImageBitmap(b1);
        Bitmap b2 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_mobile_tools, 500, 500);
        ivMobileTools.setImageBitmap(b2);
        Bitmap b3 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_sim_card, 500, 500);
        ivSimInfomatoin.setImageBitmap(b3);
        Bitmap b4 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_bank_info, 500, 500);
        ivBankInfomation.setImageBitmap(b4);
        Bitmap b5 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_card_nearby, 500, 500);
        ivNearBy.setImageBitmap(b5);
        Bitmap b6 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_trafic_find, 500, 500);
        ivFindTrafic.setImageBitmap(b6);
        Bitmap b7 = decodeSampledBitmapFromResource(getResources(), R.drawable.card_nav_compass, 500, 500);
        ivNavCompass.setImageBitmap(b7);

        ivNumberLocation.setOnClickListener(mListener);
        ivMobileTools.setOnClickListener(mListener);
        ivSimInfomatoin.setOnClickListener(mListener);
        ivBankInfomation.setOnClickListener(mListener);
        ivNearBy.setOnClickListener(mListener);
        ivFindTrafic.setOnClickListener(mListener);
        ivNavCompass.setOnClickListener(mListener);

    }


    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivNumberLocation:
                    Intent intent = new Intent(HomeActivity.this, NomLocationActivity.class);
                    MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    break;

                case R.id.ivMobileTools:
                    Intent intent4 = new Intent(HomeActivity.this, PhoneToolActivity.class);
                    MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent4, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    break;

                case R.id.ivSimInfomatoin:
                    Intent intent3 = new Intent(HomeActivity.this, SimInfomationActivity.class);
                    MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent3, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    break;

                case R.id.ivBankInfomation:
                    Intent intent2 = new Intent(HomeActivity.this, BankInfomationActivity.class);
                    MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent2, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    break;

                case R.id.ivNearBy:
                    Intent intent6 = new Intent(HomeActivity.this, FindTraficActivity.class);
                    MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent6, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    break;

                case R.id.ivFindTrafic:
                    if (checkPermissionLocation(HomeActivity.this)) {
                        toLocation();
                    } else {
                        requestPermissionLocation(HomeActivity.this, 111);
                    }
                    break;
                case R.id.ivNavCompass:
                    Intent intent7 = new Intent(HomeActivity.this, CompassActivity.class);
                    MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent7, false);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    break;
            }
        }
    };

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toLocation();
                } else {
                    Toast.makeText(HomeActivity.this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
        MainApp.getInstance().displayInterstitialAds(HomeActivity.this, intent, false);
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }

    @Override
    public void onBackPressed() {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);
        TextView yes = dialog.findViewById(R.id.tvYes);
        TextView no = dialog.findViewById(R.id.tvNo);
        ImageView banner = dialog.findViewById(R.id.ivBanner);
        banner.setImageResource(R.drawable.ic_banner);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -1;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        yes.setOnClickListener(v -> {
            dialog.dismiss();

            finish();

        });

        no.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

//
//    @Override
//    public void onBackPressed() {
//
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.layout_rate);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
//        TextView feedback = dialog.findViewById(R.id.tvFeedback);
//        TextView tvExit = dialog.findViewById(R.id.tvExit);
//        ImageView banner = dialog.findViewById(R.id.ivBanner);
//        banner.setImageResource(R.drawable.ic_rate_banner);
//
//
//        feedback.setOnClickListener(v -> {
//            Intent intent1 = new Intent(Intent.ACTION_VIEW);
//            intent1.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
//            startActivity(intent1);
//            dialog.dismiss();
//        });
//
//        tvExit.setOnClickListener(v -> finish());
//
//        dialog.show();
//
//
//    }


}
