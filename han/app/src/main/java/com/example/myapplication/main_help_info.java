package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_help_info extends AppCompatActivity {
    View.OnClickListener cl;
    Button sc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_help_info);

        sc = (Button) findViewById(R.id.success);
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.success:
                        Toast toast = Toast.makeText(getApplicationContext(), "신청완료.", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), map_help_list.class);
                        startActivity(intent);
                        break;

                }

            }
        };
        sc.setOnClickListener(cl);

    }
}