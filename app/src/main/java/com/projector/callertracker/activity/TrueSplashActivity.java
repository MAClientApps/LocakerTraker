package com.projector.callertracker.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.projector.callertracker.R;

public class TrueSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_splash);

        Intent intent = new Intent(TrueSplashActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();

    }
}