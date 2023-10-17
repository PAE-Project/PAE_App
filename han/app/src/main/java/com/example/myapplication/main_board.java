package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_board extends AppCompatActivity {
    Button free, study, support, know;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board);
        free = (Button) findViewById(R.id.free);
        study = (Button) findViewById(R.id.study);
        support = (Button) findViewById(R.id.support);
        know = (Button) findViewById(R.id.know);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.free:
                        Intent intent = new Intent(getApplicationContext(), main_board1.class);
                        startActivity(intent);
                        break;
                    case R.id.study:
                        intent = new Intent(getApplicationContext(), main_board2.class);
                        startActivity(intent);
                        break;
                    case R.id.support:
                        intent = new Intent(getApplicationContext(), main_board3.class);
                        startActivity(intent);
                        break;
                    case R.id.know:
                        intent = new Intent(getApplicationContext(), main_board4.class);
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