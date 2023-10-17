package com.example.myapplication;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class login_sign_up1 extends AppCompatActivity {
    Button go_0003;
    RadioButton help, helper;
    private RadioGroup radioGroup;
    Switch switch1, switch2, switch3;
    boolean help_data = true;
    View.OnClickListener cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up1);

        go_0003 = (Button) findViewById(R.id.go_0003);
        help = (RadioButton) findViewById(R.id.help);
        helper = (RadioButton) findViewById(R.id.helper);

        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);
        switch3 = (Switch) findViewById(R.id.switch3);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);


        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_0003:
                        Intent intent = new Intent(getApplicationContext(), login_sign_up2.class);
                        intent.putExtra("helper",help_data);

                        startActivity(intent);
                        break;
                }

            }
        };
        go_0003.setOnClickListener(cl);
        help.setOnClickListener(cl);
        helper.setOnClickListener(cl);
    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.help) {
                help_data = true;
            }
            else if(i ==  R.id.helper) {
                help_data = false;
            }
        }
    };
}