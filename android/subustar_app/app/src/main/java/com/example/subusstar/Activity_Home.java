package com.example.subusstar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Activity_Home extends AppCompatActivity {
    SQLHandler sqlHandler;

    TextView text_home_nm, text_home_ab, text_home_lo,
             text_home_we, text_home_bl, text_home_bt, text_home_bv;

    String ur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sqlHandler = new SQLHandler(
                "subustar", "cit", "citcitcit", "27.35.9.249", "60000");
        sqlHandler.connect();

        text_home_nm = (TextView)findViewById(R.id.text_home_nm);

        ur = getIntent().getStringExtra("ur");

        text_home_nm.setText(ur);
    }
}
