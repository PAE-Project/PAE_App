package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login_0001 extends AppCompatActivity {
    TextView go_0006;
    View.OnClickListener cl;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0001);

        go_0006 = (TextView) findViewById(R.id.SignIn);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.SignIn:
                        Intent intent = new Intent(getApplicationContext(), login_0006.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_0006.setOnClickListener(cl);
    }
}
