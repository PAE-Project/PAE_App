package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login_0007 extends AppCompatActivity {
    Button go_0006;
    EditText email, password, passwordCheck;
    String em, pa, paCh;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0007);

        go_0006 = (Button) findViewById(R.id.go_0006);
        email = (EditText) findViewById(R.id.email_0007);
        password = (EditText) findViewById(R.id.password_0007);
        passwordCheck = (EditText) findViewById(R.id.passwordCheck_0007);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_0006:
//                        em = email.getText().toString();
//                        pa = password.getText().toString();
//                        paCh = passwordCheck.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), login_0006.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_0006.setOnClickListener(cl);

    }
}
