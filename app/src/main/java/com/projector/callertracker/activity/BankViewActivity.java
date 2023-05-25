package com.projector.callertracker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;


public class BankViewActivity extends BaseActivity {

    String title,balance,customer;
    int img;

    TextView balanceNumber;
    TextView customerNumber;
    CardView balanceCard, customerCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RelativeLayout rl_adplaceholder= findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, BankViewActivity.this,1);

        title = getIntent().getStringExtra("title");
        ( (TextView)findViewById(R.id.tv_title)).setText(title);
        balance = getIntent().getStringExtra("balance");
        customer = getIntent().getStringExtra("customer");
        img = getIntent().getIntExtra("img",0);

        balanceNumber = findViewById(R.id.check_balance_number);
        customerNumber =findViewById(R.id.customer_care_number);
        balanceCard = findViewById(R.id.check_balance_card);
        customerCard =findViewById(R.id.customer_care_card);

        balanceNumber.setText(balance);
        customerNumber.setText(customer);

        balanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + balance)));
            }
        });

        customerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + customer)));
            }
        });

    }
}
