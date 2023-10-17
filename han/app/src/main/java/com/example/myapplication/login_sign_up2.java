package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class login_sign_up2 extends AppCompatActivity {
    EditText email, password, passwordCheck;
    Button go_0004;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up2);

        go_0004 = (Button) findViewById(R.id.go_0004);
        email = (EditText) findViewById(R.id.email_0003);
        password = (EditText) findViewById(R.id.password_0003);
        passwordCheck = (EditText) findViewById(R.id.passwordCheck_0003);
        Intent getIntent = getIntent();
        boolean helper = getIntent.getBooleanExtra("helper",true);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login_sign_up3.class);
                switch (v.getId()) {
                    case R.id.go_0004:
                        intent.putExtra("pw",password.getText().toString());
                        intent.putExtra("email",email.getText().toString());
                        intent.putExtra("helper",helper);
                        startActivity(intent);
                        break;

                }
            }
        };
        go_0004.setOnClickListener(cl);

    }

}
