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

public class main_1007_1008_1009 extends AppCompatActivity {
    Button go_1007, go_1008, go_1009;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_1007_1008_1009);
        FrameLayout contentFrame = findViewById(R.id.content_frame); // 1. 기반이 되는 FrameLayout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 2. inflater 생성
        inflater.inflate(R.layout.main_1007,contentFrame,true); // 3. (넣을 xml 파일명, 기반 layout 객체, true)
        go_1007 = (Button) findViewById(R.id.go_1007);
        go_1008 = (Button) findViewById(R.id.go_1008);
        go_1009 = (Button) findViewById(R.id.go_1009);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_1007:
                        inflater.inflate(R.layout.main_1007,contentFrame,true);
                        break;
                    case R.id.go_1008:
                        inflater.inflate(R.layout.main_1008,contentFrame,true);
                        break;
                    case R.id.go_1009:
                        inflater.inflate(R.layout.main_1009,contentFrame,true);
                        break;
                }

            }
        };
        go_1007.setOnClickListener(cl);
        go_1008.setOnClickListener(cl);
        go_1009.setOnClickListener(cl);
    }
}