package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_share extends AppCompatActivity {
    Button transaction, share, go_register;
    View.OnClickListener cl;
    String email, nickname, address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_share);
        transaction = (Button) findViewById(R.id.transaction);
        go_register = (Button) findViewById(R.id.go_register);
        share = (Button) findViewById(R.id.share);

        Intent intent = getIntent();
        email = intent.getStringExtra("이메일");
        nickname = intent.getStringExtra("닉네임");
        address = intent.getStringExtra("주소");
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.transaction:
                        Intent intent = new Intent(getApplicationContext(), main_share1.class);
                        startActivity(intent);
                        break;
                    case R.id.share:
                        intent = new Intent(getApplicationContext(), main_share2.class);
                        startActivity(intent);
                        break;

                    case R.id.go_register:
                        intent = new Intent(getApplicationContext(), RegisterActivity_share.class);
                        intent.putExtra("닉네임",nickname);
                        intent.putExtra("주소",address);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_register.setOnClickListener(cl);
        transaction.setOnClickListener(cl);
        share.setOnClickListener(cl);
    }
}