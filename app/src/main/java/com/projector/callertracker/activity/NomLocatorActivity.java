package com.projector.callertracker.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projector.callertracker.MainApp;
import com.projector.callertracker.R;
import com.projector.callertracker.model.NumLocModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class NomLocatorActivity extends BaseActivity {

    private List<NumLocModel> list = new ArrayList<>();
    private LinearLayout lldata;
    private TextView tvNumber, tvCompany, tvCity, tv_search;
    private EditText et_search;
    private String mNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_locator);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = obj.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String mCode = jsonObject.optString("mCode");
                String mCompnay = jsonObject.optString("mCompnay");
                String mCity = jsonObject.optString("mCity");

                NumLocModel mModel = new NumLocModel(mCode, mCompnay, mCity);
                list.add(mModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RelativeLayout rl_adplaceholder = findViewById(R.id.rl_adplaceholder);
        MainApp.getInstance().loadNativeAdBig(rl_adplaceholder, NomLocatorActivity.this,0);
        tv_search = findViewById(R.id.tv_search);
        et_search = findViewById(R.id.et_search);
        lldata = findViewById(R.id.lldata);
        tvNumber = findViewById(R.id.tvNumber);
        tvCompany = findViewById(R.id.tvCompany);
        tvCity = findViewById(R.id.tvCity);


        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_search.getText().toString().trim())) {
                    et_search.setError("Model_Nom can't be empty");
                } else if (et_search.getText().toString().length() < 10) {
                    et_search.setError("Please enter 10 digit number");
                } else {
                    mNumber = et_search.getText().toString();
                    searchFilter(mNumber.substring(0, 4));
                }
            }
        });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void searchFilter(String text) {
        List<NumLocModel> temp = new ArrayList();
        for (NumLocModel model : list) {
            if (model.getName().toLowerCase().contains(text)) {
                temp.add(model);
            }
        }
        showData(temp);
    }

    private void showData(List<NumLocModel> temp) {
        if (temp.size()<=0){
            Toast.makeText(this, "Not Found " + mNumber + " number!!", Toast.LENGTH_SHORT).show();
        }else {
            lldata.setVisibility(View.VISIBLE);
            NumLocModel model = temp.get(0);
            tvNumber.setText(":     " + mNumber);
            tvCompany.setText(":     " + model.getUrl());
            tvCity.setText(":     " + model.getImg());
        }


    }
}
