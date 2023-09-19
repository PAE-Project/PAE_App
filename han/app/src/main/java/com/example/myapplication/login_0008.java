package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class login_0008 extends AppCompatActivity {
    Button go_0006;
    EditText phoneNum;
    String ph;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0008);

        go_0006 = (Button) findViewById(R.id.go_0006);
        phoneNum = (EditText) findViewById(R.id.Phonenum_0008);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_0006:
//                        ph = phoneNum.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), login_0006.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        go_0006.setOnClickListener(cl);
    }
}
