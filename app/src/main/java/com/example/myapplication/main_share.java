package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_share extends AppCompatActivity {
    Button transaction, share;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_share);
        transaction = (Button) findViewById(R.id.transaction);
        share = (Button) findViewById(R.id.share);
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
                }

            }
        };
        transaction.setOnClickListener(cl);
        share.setOnClickListener(cl);
    }
}