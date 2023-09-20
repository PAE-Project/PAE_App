package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.zip.Inflater;

public class main_3001_3002_3003 extends AppCompatActivity {
    Button go_3001, go_3002, go_3003;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_3001_3002_3003);
        go_3001 = (Button) findViewById(R.id.go_3001);
        go_3002 = (Button) findViewById(R.id.go_3002);
        go_3003 = (Button) findViewById(R.id.go_3003);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_3001:
                        Intent intent = new Intent(getApplicationContext(), main_3001.class);
                        startActivity(intent);
                        break;
                    case R.id.go_3002:
                        intent = new Intent(getApplicationContext(), main_3002.class);
                        startActivity(intent);
                        break;
                    case R.id.go_3003:
                        intent = new Intent(getApplicationContext(), main_3003.class);
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