package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_info extends AppCompatActivity {
    Button go_info1, go_info2;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_info);
        go_info1 = (Button) findViewById(R.id.go_info1);
        go_info2 = (Button) findViewById(R.id.go_info2);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_info1:
                        Intent intent = new Intent(getApplicationContext(), main_info1.class);
                        startActivity(intent);
                        break;
                    case R.id.go_info2:
                        intent = new Intent(getApplicationContext(), main_info2.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_info1.setOnClickListener(cl);
        go_info2.setOnClickListener(cl);
    }
}