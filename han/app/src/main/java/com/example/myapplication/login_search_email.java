package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class login_search_email extends AppCompatActivity {
    Button go_login, check;
    EditText phoneNum;
    String phone;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_search_email);

        go_login = (Button) findViewById(R.id.go_login);
        check = (Button) findViewById(R.id.check);
        phoneNum = (EditText) findViewById(R.id.phone);


        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go_login:
                        Intent intent = new Intent(getApplicationContext(), login_sign_in.class);
                        startActivity(intent);
                        break;
                    case R.id.check:
                        connect con = new connect();
                        con.start();
                        break;
                }

            }
        };
        check.setOnClickListener(cl);
        go_login.setOnClickListener(cl);
    }
    class connect extends Thread {
        @Override
        public void run() {
            try {
                phone = phoneNum.getText().toString();
                URL url = new URL("http://3.35.45.245:8080/api/user/phone/"+phone);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET"); //전송방식
                connection.setDoOutput(false);       //데이터를 쓸 지 설정
                connection.setDoInput(true);        //데이터를 읽어올지 설정

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                // 출력물의 라인과 그 합에 대한 변수.
                StringBuilder result = new StringBuilder();
                String line;


                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }


                final String data = result.toString();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "이메일" + data, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }, 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
