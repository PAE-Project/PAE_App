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
    Button go_consult;
    TextView consultant, email, address, info;
    View.OnClickListener cl;
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
        go_consult = (Button) findViewById(R.id.go_consult);
        consultant = (TextView) findViewById(R.id.consultant);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        info = (TextView) findViewById(R.id.info);

        consultant.setText(name);
        email.setText(email_data);
        if(cost.equals("0")){
            if(gender.equals("1")){
                info.setText(detail_data+"\n"+"가격 : 무료\n"+"성별 : 남");
            } else{
                info.setText(detail_data+"\n"+"가격 : 무료\n"+"성별 : 여");
            }
        } else{
            if(gender.equals("1")){
                info.setText(detail_data+"\n"+"가격 : "+cost+"원\n"+"성별 : 남");
            } else{
                info.setText(detail_data+"\n"+"가격 : "+cost+"원\n"+"성별 : 여");
            }
        }

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_consult:
                        Toast toast = Toast.makeText(getApplicationContext(), "예약완료",Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        break;
                }

            }
        };
        go_consult.setOnClickListener(cl);
    }
}