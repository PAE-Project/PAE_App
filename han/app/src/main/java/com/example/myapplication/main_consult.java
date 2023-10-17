package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_consult extends AppCompatActivity {
    Button go_3001, go_3002, go_3003;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_consult);
        go_3001 = (Button) findViewById(R.id.go_3001);
        go_3002 = (Button) findViewById(R.id.go_3002);
        go_3003 = (Button) findViewById(R.id.go_3003);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_3001:
                        Intent intent = new Intent(getApplicationContext(), main_consult1.class);
                        startActivity(intent);
                        break;
                    case R.id.go_3002:
                        intent = new Intent(getApplicationContext(), main_consult2.class);
                        startActivity(intent);
                        break;
                    case R.id.go_3003:
                        intent = new Intent(getApplicationContext(), main_consult3.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_3001.setOnClickListener(cl);
        go_3002.setOnClickListener(cl);
        go_3003.setOnClickListener(cl);
    }
}