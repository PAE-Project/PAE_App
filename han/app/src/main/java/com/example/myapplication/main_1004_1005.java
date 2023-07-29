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

public class main_1004_1005 extends AppCompatActivity {
    Button go_1004, go_1005, go_1001;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_1004_1005);
        FrameLayout contentFrame = findViewById(R.id.content_frame); // 1. 기반이 되는 FrameLayout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 2. inflater 생성
        inflater.inflate(R.layout.main_1004,contentFrame,true); // 3. (넣을 xml 파일명, 기반 layout 객체, true)
        go_1001 = (Button) findViewById(R.id.go_1001);
        go_1004 = (Button) findViewById(R.id.go_1004);
        go_1005 = (Button) findViewById(R.id.go_1005);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_1001:
                        Intent intent = new Intent(getApplicationContext(), main_1006.class);
                        startActivity(intent);
                        break;
                    case R.id.go_1004:
                        go_1004.setSelected(true);
                        go_1005.setSelected(false);
                        inflater.inflate(R.layout.main_1004,contentFrame,true);
                        break;
                    case R.id.go_1005:
                        go_1004.setSelected(false);
                        go_1005.setSelected(true);
                        inflater.inflate(R.layout.main_1005,contentFrame,true);
                        break;
                }

            }
        };
        go_1001.setOnClickListener(cl);
        go_1004.setOnClickListener(cl);
        go_1005.setOnClickListener(cl);
    }
}