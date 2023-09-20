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

public class main_7001 extends AppCompatActivity {
    Button free, study, support, know;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_7001);
        free = (Button) findViewById(R.id.free);
        study = (Button) findViewById(R.id.study);
        support = (Button) findViewById(R.id.support);
        know = (Button) findViewById(R.id.know);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.free:
                        Intent intent = new Intent(getApplicationContext(), main_7001_1.class);
                        startActivity(intent);
                        break;
                    case R.id.study:
                        intent = new Intent(getApplicationContext(), main_7001_2.class);
                        startActivity(intent);
                        break;
                    case R.id.support:
                        intent = new Intent(getApplicationContext(), main_7001_3.class);
                        startActivity(intent);
                        break;
                    case R.id.know:
                        intent = new Intent(getApplicationContext(), main_7001_4.class);
                        startActivity(intent);
                        break;

                }

            }
        };
        free.setOnClickListener(cl);
        study.setOnClickListener(cl);
        support.setOnClickListener(cl);
        know.setOnClickListener(cl);
    }
}