package com.projector.callertracker.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;
import com.projector.callertracker.utils.SharedPreferencesClass;


public class StartAppActivity extends BaseActivity {

    private static final int PERMISSION_READ_STATE = 1111;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        SharedPreferencesClass.getInstance().setBoolean("isSplash", false);

        findViewById(R.id.llPolicy).setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse("https://true-id-caller-name-0.flycricket.io/privacy.html"));
            startActivity(intent1);
        });


        findViewById(R.id.llShare).setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "miracast");
                String shareMessage = "\nDownload True ID Caller Name & Location Tracker App For track your Number location.\n\n" +
                        "Download Link Here.\n" + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share"));
            } catch (Exception e) {
                Log.i("Share", e.getMessage());
            }
        });

        findViewById(R.id.ivStart).setOnClickListener(v -> {
            if (checkPermissionLocation()) {
                toMain();
            } else {
                takePermission();
            }
        });

    }

    public void toMain() {
        Intent intent = new Intent(StartAppActivity.this, HomeActivity.class);
        MainApp.getInstance().displayInterstitialAds(StartAppActivity.this, intent, true);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == PERMISSION_READ_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toMain();
            } else {
                takePermission();
            }
        }
    }

    public void takePermission() {
        if (ContextCompat.checkSelfPermission(StartAppActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StartAppActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }
    }

    public boolean checkPermissionLocation() {
        int result = ContextCompat.checkSelfPermission(StartAppActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

}
