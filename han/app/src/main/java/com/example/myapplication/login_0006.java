package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login_0006 extends AppCompatActivity {
    TextView go_0002, go_0007, go_0008;
    Button go_1001;
    EditText email, password;
    String em, pa;
    View.OnClickListener cl;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0006);

        go_0002 = (TextView) findViewById(R.id.SignUp);
        go_0008 = (TextView) findViewById(R.id.femail);
        go_0007 = (TextView) findViewById(R.id.fpassword);
        go_1001 = (Button) findViewById(R.id.go_1001);
        email = (EditText) findViewById(R.id.email_0006);
        password = (EditText) findViewById(R.id.password_0006);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.SignUp:
                        Intent intent = new Intent(getApplicationContext(), login_0002.class);
                        startActivity(intent);
                        break;

                    case R.id.femail:
                        intent = new Intent(getApplicationContext(), login_0008.class);
                        startActivity(intent);
                        break;

                    case R.id.fpassword:
                        intent = new Intent(getApplicationContext(), login_0007.class);
                        startActivity(intent);
                        break;

                    case R.id.go_1001:
//                        em = email.getText().toString();
//                        pa = password.getText().toString();
                        intent = new Intent(getApplicationContext(), main_1001.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_0002.setOnClickListener(cl);
        go_0007.setOnClickListener(cl);
        go_0008.setOnClickListener(cl);
        go_1001.setOnClickListener(cl);
    }
}
