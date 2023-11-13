package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_help_info extends AppCompatActivity {
    TextView helper, tel, ad, info;
    View.OnClickListener cl;
    Button sc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_help_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("이름");
        String address = intent.getStringExtra("주소");
        String age = intent.getStringExtra("나이");
        String phone = intent.getStringExtra("전화번호");
        String gender = intent.getStringExtra("성별");
        sc = (Button) findViewById(R.id.success);
        helper = (TextView) findViewById(R.id.helper);
        tel = (TextView) findViewById(R.id.tel);
        ad = (TextView) findViewById(R.id.ad);
        info = (TextView) findViewById(R.id.info);
        System.out.println(age);
        helper.setText(name);
        tel.setText(phone);
        ad.setText(address);
        info.setText(gender + ", " + age);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.success:
                        finish();
                        break;

                }

            }
        };
        sc.setOnClickListener(cl);

    }
}