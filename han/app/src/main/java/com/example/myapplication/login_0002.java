package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class login_0002 extends AppCompatActivity {
    Button go_0003, help, helper;
    Switch switch1, switch2, switch3;
    View.OnClickListener cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0002);

        go_0003 = (Button) findViewById(R.id.go_0003);
        help = (Button) findViewById(R.id.help);
        helper = (Button) findViewById(R.id.helper);
        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);
        switch3 = (Switch) findViewById(R.id.switch3);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_0003:
                        Intent intent = new Intent(getApplicationContext(), login_0003.class);
                        startActivity(intent);
                        break;
                    case R.id.help:
                        help.setSelected(true);
                        helper.setSelected(false);
                        break;
                    case R.id.helper:
                        help.setSelected(false);
                        helper.setSelected(true);
                        break;
                }

            }
        };


        go_0003.setOnClickListener(cl);
        help.setOnClickListener(cl);
        helper.setOnClickListener(cl);
    }
}