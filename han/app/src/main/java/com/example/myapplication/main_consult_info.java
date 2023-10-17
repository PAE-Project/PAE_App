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
    TextView consultant, phonenum, address, info;
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
        go_consult = (Button) findViewById(R.id.go_consult);
        consultant = (TextView) findViewById(R.id.consultant);
        phonenum = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        info = (TextView) findViewById(R.id.info);

        consultant.setText(name);
        info.setText(field+", "+cost+", "+gender);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_consult:
                        Toast toast = Toast.makeText(getApplicationContext(), "예약완료",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), main_consult3.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_consult.setOnClickListener(cl);
    }
}