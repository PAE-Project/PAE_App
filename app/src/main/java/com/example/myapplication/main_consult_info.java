package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_consult_info extends AppCompatActivity {
    TextView consultant, email, price_tv, info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_consult_info);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String cost = intent.getStringExtra("cost");
        String gender = intent.getStringExtra("gender");
        String field = intent.getStringExtra("field");
        String email_data = intent.getStringExtra("email");
        String detail_data = intent.getStringExtra("detail");
        consultant = (TextView) findViewById(R.id.consultant);
        email = (TextView) findViewById(R.id.email);
        price_tv = (TextView) findViewById(R.id.price_tv);
        info = (TextView) findViewById(R.id.info);

        consultant.setText(name);
        email.setText(email_data);
        if(cost.equals("0")){
            price_tv.setText("무료");
            if(gender.equals("1")){
                info.setText(detail_data+"성별 : 남");
            } else{
                info.setText(detail_data+"성별 : 여");
            }
        } else{
            price_tv.setText(cost);
            if(gender.equals("1")){
                info.setText(detail_data+"성별 : 남");
            } else{
                info.setText(detail_data+"성별 : 여");
            }
        }

    }
}