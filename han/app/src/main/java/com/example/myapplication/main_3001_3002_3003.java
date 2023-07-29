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

public class main_3001_3002_3003 extends AppCompatActivity {
    Button go_3001, go_3002, go_3003;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_3001_3002_3003);
        FrameLayout contentFrame = findViewById(R.id.content_frame); // 1. 기반이 되는 FrameLayout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 2. inflater 생성
        inflater.inflate(R.layout.main_3001,contentFrame,true); // 3. (넣을 xml 파일명, 기반 layout 객체, true)
        go_3001 = (Button) findViewById(R.id.go_3001);
        go_3002 = (Button) findViewById(R.id.go_3002);
        go_3003 = (Button) findViewById(R.id.go_3003);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_3001:
                        inflater.inflate(R.layout.main_3001,contentFrame,true);
                        break;
                    case R.id.go_3002:
                        inflater.inflate(R.layout.main_3002,contentFrame,true);
                        break;
                    case R.id.go_3003:
                        inflater.inflate(R.layout.main_3003,contentFrame,true);
                        break;
                }

            }
        };
        go_3001.setOnClickListener(cl);
        go_3002.setOnClickListener(cl);
        go_3003.setOnClickListener(cl);
    }
}