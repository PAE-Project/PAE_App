package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button reg;
    View.OnClickListener cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg = (Button) findViewById(R.id.reg_button);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.reg_button:
                        Intent intent = new Intent(getApplicationContext(), main_7001_1.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        reg.setOnClickListener(cl);
    }
}
