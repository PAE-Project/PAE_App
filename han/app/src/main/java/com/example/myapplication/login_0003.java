package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class login_0003 extends AppCompatActivity {
    EditText email, password, passwordCheck;
    Button go_0004, help, helper;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0003);

        go_0004 = (Button) findViewById(R.id.go_0004);
        email = (EditText) findViewById(R.id.email_0003);
        password = (EditText) findViewById(R.id.password_0003);
        passwordCheck = (EditText) findViewById(R.id.passwordCheck_0003);
        help = (Button) findViewById(R.id.help);
        helper = (Button) findViewById(R.id.helper);


        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login_0004.class);
                switch (v.getId()) {
                    case R.id.go_0004:
                        intent.putExtra("pw",password.getText().toString());
                        intent.putExtra("email",email.getText().toString());
                        startActivity(intent);
                        break;

                    case R.id.help:
                        help.setSelected(true);
                        helper.setSelected(false);
                        intent.putExtra("helper",false);
                        break;
                    case R.id.helper:
                        help.setSelected(false);
                        helper.setSelected(true);
                        intent.putExtra("helper",true);
                        break;
                }
            }
        };
        go_0004.setOnClickListener(cl);

    }
}
