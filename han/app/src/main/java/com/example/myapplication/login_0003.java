package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login_0003 extends AppCompatActivity {
    EditText email, password, passwordCheck;
    Button go_0004;
    String em, pa, paCh;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0003);

        go_0004 = (Button) findViewById(R.id.go_0004);
        email = (EditText) findViewById(R.id.email_0003);
        password = (EditText) findViewById(R.id.password_0003);
        passwordCheck = (EditText) findViewById(R.id.passwordCheck_0003);



        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_0004:
//                        em = email.getText().toString();
//                        pa = password.getText().toString();
//                        paCh = passwordCheck.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), login_0004.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        go_0004.setOnClickListener(cl);
    }
}
